terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

provider "aws" {
  region = "us-east-1"
}

# 1. Grupo de Seguridad (Abre el puerto 8080 para tu API y 22 para SSH)
resource "aws_security_group" "franchise_sg" {
  name        = "franchise_api_sg"
  description = "Permitir trafico HTTP para Swagger y API"

  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

data "aws_ami" "amazon_linux" {
  most_recent = true
  owners      = ["amazon"]

  filter {
    name   = "name"
    values = ["al2023-ami-2023.*-x86_64"]
  }
}

resource "aws_instance" "app_server" {
  ami             = data.aws_ami.amazon_linux.id
  instance_type   = "t3.micro"
  security_groups = [aws_security_group.franchise_sg.name]

  # 4. Script de Arranque (Instala Docker y ejecuta el Compose)
  user_data = <<-EOF
              #!/bin/bash
              yum update -y
              yum install -y docker
              service docker start
              usermod -a -G docker ec2-user
              
              # Instalar Docker Compose
              curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
              chmod +x /usr/local/bin/docker-compose
              
              # Crear el archivo docker-compose.yml
              cat <<EOT > /home/ec2-user/docker-compose.yml
              version: '3.8'
              services:
                mongodb:
                  image: mongo:latest
                  ports:
                    - "27017:27017"
                app:
                  # IMPORTANTE: Reemplaza esto con tu usuario de Docker Hub
                  image: sebasxd92/franchise-api:v1
                  ports:
                    - "8080:8080"
                  environment:
                    - SPRING_DATA_MONGODB_URI=mongodb://mongodb:27017/franchisedb
                  depends_on:
                    - mongodb
              EOT

              # Levantar la aplicación
              cd /home/ec2-user
              /usr/local/bin/docker-compose up -d
              EOF

  tags = {
    Name = "Accenture-Franchise-Production"
  }
}

output "swagger_url" {
  value = "http://${aws_instance.app_server.public_ip}:8080/webjars/swagger-ui/index.html"
}