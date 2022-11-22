CREATE SEQUENCE hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE account_ref (
  id BIGINT AUTO_INCREMENT NOT NULL,
   client_id BIGINT,
   number VARCHAR(255),
   CONSTRAINT pk_account_ref PRIMARY KEY (id)
);

CREATE TABLE bank_client (
  id BIGINT AUTO_INCREMENT NOT NULL,
   first_name VARCHAR(255),
   last_name VARCHAR(255),
   email VARCHAR(255),
   identification_number VARCHAR(255),
   CONSTRAINT pk_bank_client PRIMARY KEY (id)
);

ALTER TABLE account_ref ADD CONSTRAINT FK_ACCOUNT_REF_ON_CLIENT FOREIGN KEY (client_id) REFERENCES bank_client (id);