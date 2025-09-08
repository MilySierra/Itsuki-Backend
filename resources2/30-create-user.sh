#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    BEGIN;
        CREATE USER $DB_USER WITH PASSWORD '$DB_PASSWORD';
        GRANT CONNECT ON DATABASE $POSTGRES_DB TO $DB_USER ;
        GRANT INSERT,SELECT,UPDATE,DELETE ON ALL TABLES IN SCHEMA public TO $DB_USER;
        GRANT SELECT,UPDATE ON ALL SEQUENCES IN SCHEMA public TO $DB_USER ;
    COMMIT;
EOSQL

# Si el llamado a este archivo no funciona, puedes crear el usuario manualmente con los siguientes comandos:

# docker exec -it prisma-backend-db-1 psql -U admin -d appdb

# CREATE USER app_user WITH PASSWORD 'myappwd';
# GRANT CONNECT ON DATABASE appdb TO app_user;
# GRANT INSERT, SELECT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO app_user;
# GRANT SELECT, UPDATE ON ALL SEQUENCES IN SCHEMA public TO app_user;

# \q
# docker compose restart my-app