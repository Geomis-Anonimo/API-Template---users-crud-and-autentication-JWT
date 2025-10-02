#!/bin/bash

DB_USER="postgres"
DB_PASSWORD="root"
DB_NAME="finance_db"
PORT="5432"

docker run --name finance_control_db \
  -e POSTGRES_USER=$DB_USER \
  -e POSTGRES_PASSWORD=$DB_PASSWORD \
  -e POSTGRES_DB=$DB_NAME \
  -p $PORT:5432 \
  -d postgres:latest

sleep 5

echo "Verificando conexão com o banco de dados..."

docker exec -it postgres_users_db psql -U $DB_USER -d $DB_NAME -c "\l"

echo "Banco PostgreSQL pronto para uso."