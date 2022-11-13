CREATE TABLE bank_account (
  id BIGINT AUTO_INCREMENT NOT NULL,
   number VARCHAR(255),
   type VARCHAR(255),
   status VARCHAR(255),
   available_balance DECIMAL,
   actual_balance DECIMAL,
   client_id BIGINT,
   CONSTRAINT pk_bank_account PRIMARY KEY (id)
);

CREATE TABLE bank_client (
  id BIGINT AUTO_INCREMENT NOT NULL,
   first_name VARCHAR(255),
   last_name VARCHAR(255),
   email VARCHAR(255),
   identification_number VARCHAR(255),
   CONSTRAINT pk_bank_client PRIMARY KEY (id)
);

CREATE TABLE bank_transaction (
  id BIGINT AUTO_INCREMENT NOT NULL,
   ammount DECIMAL,
   transaction_type VARCHAR(255),
   reference_number VARCHAR(255),
   transaction_id VARCHAR(255),
   account_id BIGINT,
   CONSTRAINT pk_bank_transaction PRIMARY KEY (id)
);

CREATE TABLE bank_utility_account (
  id BIGINT AUTO_INCREMENT NOT NULL,
   number VARCHAR(255),
   provider_name VARCHAR(255),
   CONSTRAINT pk_bank_utility_account PRIMARY KEY (id)
);

ALTER TABLE bank_account ADD CONSTRAINT FK_BANK_ACCOUNT_ON_CLIENT FOREIGN KEY (client_id) REFERENCES bank_client (id);

ALTER TABLE bank_transaction ADD CONSTRAINT FK_BANK_TRANSACTION_ON_ACCOUNT FOREIGN KEY (account_id) REFERENCES bank_account (id);