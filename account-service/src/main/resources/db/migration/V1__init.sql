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

CREATE TABLE bank_transaction (
  id BIGINT AUTO_INCREMENT NOT NULL,
   transfer_id VARCHAR(255) NOT NULL,
   ammount DECIMAL,
   transaction_type VARCHAR(255),
   reference_number VARCHAR(255),
   transaction_id VARCHAR(255),
   account_id BIGINT,
   CONSTRAINT pk_bank_transaction PRIMARY KEY (id)
);