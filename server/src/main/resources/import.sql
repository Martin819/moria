# Transactions: 20 zkusebnich radku, 1 ucet, 2 karty, prijmy celkem 22000, vydaje 20000, 4 typy plateb, nektere platby uz kategorizovane
INSERT INTO moria.transactions (id, account_id, card_number, mcc, merchant_name, constant_symbol, specific_symbol, variable_symbol, exchange_rate, foreign_amount, foreign_currency, booking_date, category_id, direction, party_account_number, party_bank_code, party_prefix, party_description, payee_message, payer_message, transaction_fee, transaction_fee_canceled, transaction_type, user_description, amount, currency, value_date) VALUES (1, 6666, '3542658921352648', '3560', 'Billa', null, null, null, null, null, null, '2018-03-11 22:08:21', 111, 'OUTGOING', null, null, null, null, null, null, null, null, 'CARD', null, 1521.00, 'CZK', '2018-03-12 07:29:57');
INSERT INTO moria.transactions (id, account_id, card_number, mcc, merchant_name, constant_symbol, specific_symbol, variable_symbol, exchange_rate, foreign_amount, foreign_currency, booking_date, category_id, direction, party_account_number, party_bank_code, party_prefix, party_description, payee_message, payer_message, transaction_fee, transaction_fee_canceled, transaction_type, user_description, amount, currency, value_date) VALUES (2, 6666, '3542658921352648', '4567', 'McDonalds', '0', '0', '0', null, null, null, '2018-10-06 19:55:20', 0, 'INCOMING', '654651579612', '0600', '00000865', 'Im Lovin It', 'vyplata', null, null, null, 'PAYMENT_HOME', 'Muj zamestanavatel', 17325.00, 'CZK', '2018-10-07 17:45:44');
INSERT INTO moria.transactions (id, account_id, card_number, mcc, merchant_name, constant_symbol, specific_symbol, variable_symbol, exchange_rate, foreign_amount, foreign_currency, booking_date, category_id, direction, party_account_number, party_bank_code, party_prefix, party_description, payee_message, payer_message, transaction_fee, transaction_fee_canceled, transaction_type, user_description, amount, currency, value_date) VALUES (3, 6666, null, null, null, '0', '0', '20190125', null, null, null, '2018-12-31 00:26:57', 0, 'OUTGOING', '770418267290', '3030', '00000000', 'Vodafone', '0', 'predrazeny tarif', null, null, 'PAYMENT_HOME', 'Muj operator', 500.00, 'CZK', '2018-12-31 12:26:57');
INSERT INTO moria.transactions (id, account_id, card_number, mcc, merchant_name, constant_symbol, specific_symbol, variable_symbol, exchange_rate, foreign_amount, foreign_currency, booking_date, category_id, direction, party_account_number, party_bank_code, party_prefix, party_description, payee_message, payer_message, transaction_fee, transaction_fee_canceled, transaction_type, user_description, amount, currency, value_date) VALUES (4, 6666, '3542658921352648', '3181', 'Sportisimo', null, null, null, null, null, null, '2018-03-07 16:51:48', 0, 'OUTGOING', null, null, null, null, null, null, null, null, 'CARD', null, 899.00, 'CZK', '2018-03-09 09:39:48');
INSERT INTO moria.transactions (id, account_id, card_number, mcc, merchant_name, constant_symbol, specific_symbol, variable_symbol, exchange_rate, foreign_amount, foreign_currency, booking_date, category_id, direction, party_account_number, party_bank_code, party_prefix, party_description, payee_message, payer_message, transaction_fee, transaction_fee_canceled, transaction_type, user_description, amount, currency, value_date) VALUES (5, 6666, null, null, null, '0', '5387662130', '0', null, null, null, '2018-10-24 13:39:49', 0, 'INCOMING', '162978789688', '2700', '00000000', '0', 'splatka dluhu', null, null, null, 'PAYMENT_HOME', null, 4200.00, 'CZK', '2018-10-25 10:32:37');
INSERT INTO moria.transactions (id, account_id, card_number, mcc, merchant_name, constant_symbol, specific_symbol, variable_symbol, exchange_rate, foreign_amount, foreign_currency, booking_date, category_id, direction, party_account_number, party_bank_code, party_prefix, party_description, payee_message, payer_message, transaction_fee, transaction_fee_canceled, transaction_type, user_description, amount, currency, value_date) VALUES (6, 6666, null, '6821', 'Hypotecni banka', null, null, null, null, null, null, '2018-04-02 11:06:54', 0, 'OUTGOING', '785845484688', '1500', '00000001', null, null, null, null, null, 'MORTGAGE', 'Splatka hypoteky', 4099.00, 'CZK', '2018-04-04 07:59:42');
INSERT INTO moria.transactions (id, account_id, card_number, mcc, merchant_name, constant_symbol, specific_symbol, variable_symbol, exchange_rate, foreign_amount, foreign_currency, booking_date, category_id, direction, party_account_number, party_bank_code, party_prefix, party_description, payee_message, payer_message, transaction_fee, transaction_fee_canceled, transaction_type, user_description, amount, currency, value_date) VALUES (7, 6666, null, null, null, '4482', '0', '927335598', null, null, null, '2018-05-22 06:25:02', 0, 'OUTGOING', '945171781792', '0200', '00000014', '0', '0', 'najem', null, null, 'PAYMENT_HOME', 'Muj landlord', 2500.00, 'CZK', '2018-05-25 01:08:14');
INSERT INTO moria.transactions (id, account_id, card_number, mcc, merchant_name, constant_symbol, specific_symbol, variable_symbol, exchange_rate, foreign_amount, foreign_currency, booking_date, category_id, direction, party_account_number, party_bank_code, party_prefix, party_description, payee_message, payer_message, transaction_fee, transaction_fee_canceled, transaction_type, user_description, amount, currency, value_date) VALUES (8, 6666, null, null, null, '3347', '0', '4848218390', null, null, null, '2019-02-07 09:58:12', 0, 'OUTGOING', '597175768639', '2700', '00236541', null, null, 'jen tak posilam prachy random osobe', null, null, 'PAYMENT_HOME', 'random osoba', 90.00, 'CZK', '2019-02-08 20:03:00');
INSERT INTO moria.transactions (id, account_id, card_number, mcc, merchant_name, constant_symbol, specific_symbol, variable_symbol, exchange_rate, foreign_amount, foreign_currency, booking_date, category_id, direction, party_account_number, party_bank_code, party_prefix, party_description, payee_message, payer_message, transaction_fee, transaction_fee_canceled, transaction_type, user_description, amount, currency, value_date) VALUES (9, 6666, '4562356984521568', '7277', 'Burgrarna', null, null, null, null, null, null, '2018-10-23 01:43:42', 0, 'OUTGOING', null, null, null, null, null, null, null, null, 'CARD', null, 249.00, 'CZK', '2018-10-25 04:22:06');
INSERT INTO moria.transactions (id, account_id, card_number, mcc, merchant_name, constant_symbol, specific_symbol, variable_symbol, exchange_rate, foreign_amount, foreign_currency, booking_date, category_id, direction, party_account_number, party_bank_code, party_prefix, party_description, payee_message, payer_message, transaction_fee, transaction_fee_canceled, transaction_type, user_description, amount, currency, value_date) VALUES (10, 6666, '4562356984521568', '5968', 'Netflix', '0', '0', '0', null, null, null, '2018-03-16 15:58:26', 118, 'OUTGOING', null, null, null, null, '0', '0', null, null, 'CARD', null, 230.00, 'CZK', '2018-03-17 16:56:02');
INSERT INTO moria.transactions (id, account_id, card_number, mcc, merchant_name, constant_symbol, specific_symbol, variable_symbol, exchange_rate, foreign_amount, foreign_currency, booking_date, category_id, direction, party_account_number, party_bank_code, party_prefix, party_description, payee_message, payer_message, transaction_fee, transaction_fee_canceled, transaction_type, user_description, amount, currency, value_date) VALUES (11, 6666, '4562356984521568', '4899', 'Spotify', '0', '0', '0', null, null, null, '2019-02-15 21:43:19', 118, 'OUTGOING', null, null, null, '0', null, null, null, null, 'CARD', null, 85.00, 'CZK', '2019-02-18 17:24:07');
INSERT INTO moria.transactions (id, account_id, card_number, mcc, merchant_name, constant_symbol, specific_symbol, variable_symbol, exchange_rate, foreign_amount, foreign_currency, booking_date, category_id, direction, party_account_number, party_bank_code, party_prefix, party_description, payee_message, payer_message, transaction_fee, transaction_fee_canceled, transaction_type, user_description, amount, currency, value_date) VALUES (12, 6666, null, null, null, '6771', '0', '4457394676', null, null, null, '2018-05-24 01:31:58', 0, 'INCOMING', '191816107239', '0800', '00000000', '0', 'pivo patek', null, null, null, 'PAYMENT_HOME', 'amigo', 262.00, 'CZK', '2018-05-25 11:36:46');
INSERT INTO moria.transactions (id, account_id, card_number, mcc, merchant_name, constant_symbol, specific_symbol, variable_symbol, exchange_rate, foreign_amount, foreign_currency, booking_date, category_id, direction, party_account_number, party_bank_code, party_prefix, party_description, payee_message, payer_message, transaction_fee, transaction_fee_canceled, transaction_type, user_description, amount, currency, value_date) VALUES (13, 6666, '4562356984521568', '3560', 'Billa', null, null, null, null, null, null, '2018-11-28 13:26:16', 0, 'OUTGOING', null, null, null, null, null, null, null, null, 'CARD', null, 2392.00, 'CZK', '2018-11-30 11:59:52');
INSERT INTO moria.transactions (id, account_id, card_number, mcc, merchant_name, constant_symbol, specific_symbol, variable_symbol, exchange_rate, foreign_amount, foreign_currency, booking_date, category_id, direction, party_account_number, party_bank_code, party_prefix, party_description, payee_message, payer_message, transaction_fee, transaction_fee_canceled, transaction_type, user_description, amount, currency, value_date) VALUES (14, 6666, null, null, null, '0', '6484975897', '0', null, null, null, '2018-09-26 07:26:21', 0, 'INCOMING', '528827605187', '0800', '12549856', '0', '0', '0', null, null, 'PAYMENT_HOME', null, 83.00, 'CZK', '2018-09-28 03:07:09');
INSERT INTO moria.transactions (id, account_id, card_number, mcc, merchant_name, constant_symbol, specific_symbol, variable_symbol, exchange_rate, foreign_amount, foreign_currency, booking_date, category_id, direction, party_account_number, party_bank_code, party_prefix, party_description, payee_message, payer_message, transaction_fee, transaction_fee_canceled, transaction_type, user_description, amount, currency, value_date) VALUES (15, 6666, '3542658921352648', '3110', 'Regiojet', null, null, null, null, null, null, '2018-07-23 16:01:17', 119, 'OUTGOING', null, null, null, 'Regiojet CZ', null, null, null, null, 'CARD', null, 157.00, 'CZK', '2018-07-26 10:15:41');
INSERT INTO moria.transactions (id, account_id, card_number, mcc, merchant_name, constant_symbol, specific_symbol, variable_symbol, exchange_rate, foreign_amount, foreign_currency, booking_date, category_id, direction, party_account_number, party_bank_code, party_prefix, party_description, payee_message, payer_message, transaction_fee, transaction_fee_canceled, transaction_type, user_description, amount, currency, value_date) VALUES (16, 6666, '3542658921352648', '1234', 'CSOB', null, null, null, null, null, null, '2018-03-11 06:42:10', 0, 'OUTGOING', null, null, null, null, null, null, null, null, 'CASH', 'vyber z bankomatu', 5000.00, 'CZK', '2018-03-12 02:08:34');
INSERT INTO moria.transactions (id, account_id, card_number, mcc, merchant_name, constant_symbol, specific_symbol, variable_symbol, exchange_rate, foreign_amount, foreign_currency, booking_date, category_id, direction, party_account_number, party_bank_code, party_prefix, party_description, payee_message, payer_message, transaction_fee, transaction_fee_canceled, transaction_type, user_description, amount, currency, value_date) VALUES (17, 6666, null, null, null, '3317', '0', '777', null, null, null, '2018-08-20 18:38:38', 0, 'INCOMING', '451356622949', '0800', '00000000', null, 'za listek do kina', null, null, null, 'PAYMENT_HOME', 'kamoska', 130.00, 'CZK', '2018-08-21 01:21:50');
INSERT INTO moria.transactions (id, account_id, card_number, mcc, merchant_name, constant_symbol, specific_symbol, variable_symbol, exchange_rate, foreign_amount, foreign_currency, booking_date, category_id, direction, party_account_number, party_bank_code, party_prefix, party_description, payee_message, payer_message, transaction_fee, transaction_fee_canceled, transaction_type, user_description, amount, currency, value_date) VALUES (18, 6666, '3542658921352648', '4955', 'Steam Store', null, null, null, null, null, null, '2019-01-05 14:54:42', 0, 'OUTGOING', null, null, null, null, '0', '0', null, null, 'CARD', null, 1963.00, 'CZK', '2019-01-06 14:54:42');
INSERT INTO moria.transactions (id, account_id, card_number, mcc, merchant_name, constant_symbol, specific_symbol, variable_symbol, exchange_rate, foreign_amount, foreign_currency, booking_date, category_id, direction, party_account_number, party_bank_code, party_prefix, party_description, payee_message, payer_message, transaction_fee, transaction_fee_canceled, transaction_type, user_description, amount, currency, value_date) VALUES (19, 6666, '4562356984521568', '2358', 'Bio Central', null, null, null, null, null, null, '2018-04-07 08:02:19', 124, 'OUTGOING', null, null, null, null, null, null, null, null, 'CARD', null, 260.00, 'CZK', '2018-04-08 00:50:19');
INSERT INTO moria.transactions (id, account_id, card_number, mcc, merchant_name, constant_symbol, specific_symbol, variable_symbol, exchange_rate, foreign_amount, foreign_currency, booking_date, category_id, direction, party_account_number, party_bank_code, party_prefix, party_description, payee_message, payer_message, transaction_fee, transaction_fee_canceled, transaction_type, user_description, amount, currency, value_date) VALUES (20, 6666, '4562356984521568', '4449', 'Kafekara', null, null, null, null, null, null, '2018-06-03 08:28:58', 115, 'OUTGOING', null, null, null, null, null, null, null, null, 'CARD', null, 55.00, 'CZK', '2018-06-04 08:00:10');

INSERT INTO moria.rulesets (id, booking_date_from_value, booking_date_to_value, card_number_value, category_id, constant_symbol_value, direction, merchant_name_value, party_account_number_value, party_bank_code_value, party_description_value, party_prefix_value, payee_message_value, payer_message_value, specific_symbol_value, transaction_type, user_description_value, value_from_value, value_to_value, variable_symbol_value) VALUES (3, '2019-03-20 17:36:18', '2019-03-20 18:36:22', null, 11, '116', 'INCOMING', null, null, '45628', '123456', '0700', 'Mcdonalds', 'Mcdonalds', '5', null, 'Mcdonalds', 12000.00, 20000.00, null);
INSERT INTO moria.rulesets (id, booking_date_from_value, booking_date_to_value, card_number_value, category_id, constant_symbol_value, direction, merchant_name_value, party_account_number_value, party_bank_code_value, party_description_value, party_prefix_value, payee_message_value, payer_message_value, specific_symbol_value, transaction_type, user_description_value, value_from_value, value_to_value, variable_symbol_value) VALUES (6, null, null, null, 128, null, null, null, null, null, null, null, '', null, null, 'MORTGAGE', null, null, null, null);