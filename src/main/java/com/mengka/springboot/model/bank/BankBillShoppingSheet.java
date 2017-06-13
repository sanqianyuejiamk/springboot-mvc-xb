package com.mengka.springboot.model.bank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class BankBillShoppingSheet {
    private Long id;

    @JsonProperty("bank_id")
    private Long bankId;

    @JsonProperty("bill_month")
    private Date billMonth;

    @JsonProperty("bill_id")
    private String externalId;

    @JsonProperty("card_id")
    private Long cardId;

    @JsonProperty("card_num")
    private String cardNum;

    @JsonProperty("post_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "Asia/Shanghai")
    private Date postDate;

    @JsonProperty("trans_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
    private Date transDate;

    @JsonProperty("currency_type")
    private Integer currencyType;

    @JsonProperty("amount_money")
    private BigDecimal amountMoney;

    @JsonProperty("balance")
    private BigDecimal balance;

    @JsonProperty("trans_addr")
    private String transAddr;

    @JsonProperty("trans_method")
    private String transMethod;

    @JsonProperty("trans_channel")
    private String transChannel;

    private String description;

    private String remark;

    @JsonProperty("opposite_card_no")
    private String oppositeCardNo;

    @JsonProperty("name_on_opposite_card")
    private String nameOnOppositeCard;

    @JsonProperty("opposite_bank")
    private String oppositeBank;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public Date getBillMonth() {
        return billMonth;
    }

    public void setBillMonth(Date billMonth) {
        this.billMonth = billMonth;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public Integer getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(Integer currencyType) {
        this.currencyType = currencyType;
    }

    public BigDecimal getAmountMoney() {
        return amountMoney;
    }

    public void setAmountMoney(BigDecimal amountMoney) {
        this.amountMoney = amountMoney;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getTransAddr() {
        return transAddr;
    }

    public void setTransAddr(String transAddr) {
        this.transAddr = transAddr;
    }

    public String getTransMethod() {
        return transMethod;
    }

    public void setTransMethod(String transMethod) {
        this.transMethod = transMethod;
    }

    public String getTransChannel() {
        return transChannel;
    }

    public void setTransChannel(String transChannel) {
        this.transChannel = transChannel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOppositeCardNo() {
        return oppositeCardNo;
    }

    public void setOppositeCardNo(String oppositeCardNo) {
        this.oppositeCardNo = oppositeCardNo;
    }

    public String getNameOnOppositeCard() {
        return nameOnOppositeCard;
    }

    public void setNameOnOppositeCard(String nameOnOppositeCard) {
        this.nameOnOppositeCard = nameOnOppositeCard;
    }

    public String getOppositeBank() {
        return oppositeBank;
    }

    public void setOppositeBank(String oppositeBank) {
        this.oppositeBank = oppositeBank;
    }
}