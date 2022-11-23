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