# ----------------------------------------
# TRIGGERY:     prevede nevyplnene parametry u dat z API na reprezentaci, na kterou byla aplikace navrzena (tzn. dosadi NULL misto prazdneho stringu nebo 0)
# ----------------------------------------
CREATE TRIGGER replaceEmptyStringsWithNull BEFORE INSERT ON transactions
    FOR EACH ROW
begin
    Set new.payer_message = nullif(new.payer_message,'');
    Set new.payee_message = nullif(new.payee_message,'');
    Set new.user_description = nullif(new.user_description,'');
    Set new.party_description = nullif(new.party_description,'');
    Set new.party_prefix = nullif(new.party_prefix,'');
    Set new.party_account_number = nullif(new.party_account_number,'');
    Set new.party_bank_code = nullif(new.party_bank_code,'');
    Set new.mcc = nullif(new.mcc,'');
    Set new.merchant_name = nullif(new.merchant_name,'');
    Set new.card_number = nullif(new.card_number,'');
    Set new.parent_id = nullif(new.parent_id,'');
    Set new.constant_symbol = nullif(new.constant_symbol,'');
    Set new.variable_symbol = nullif(new.variable_symbol,'');
    Set new.specific_symbol = nullif(new.specific_symbol,'');
    Set new.foreign_currency = nullif(new.foreign_currency,'');

    Set new.transaction_fee = nullif(new.transaction_fee,0);
    Set new.foreign_amount = nullif(new.foreign_amount,0);
    Set new.exchange_rate = nullif(new.exchange_rate,0);

    Set new.transaction_fee_canceled = nullif(new.exchange_rate,true);
end;
