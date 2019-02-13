package com.yaheen.geturlchange.bean;

import java.util.List;

public class USDTAcountBean {

    private List<BalanceBean> balance;

    public List<BalanceBean> getBalance() {
        return balance;
    }

    public void setBalance(List<BalanceBean> balance) {
        this.balance = balance;
    }

    public static class BalanceBean {
        /**
         * pendingpos : 0
         * reserved : 0
         * divisible : true
         * symbol : SP31
         * value : 5354652733855424
         * frozen : 0
         * pendingneg : -8424239635585
         * propertyinfo : {"subcategory":"Activities auxiliary to financial service and insurance activities","blockhash":"00000000000000001e76250b3725547b5887329cfe3a8bb930a70e66747384d3","totaltokens":"2520000000.00000000","freezingenabled":true,"issuer":"3MbYQMMmSkC3AgWkj9FMo5LsPTW1zBTwXL","category":"Financial and insurance activities","fee":"0.00010000","blocktime":1412613555,"txid":"5ed3694e8a4fa8d3ec5c75eb6789492c69e65511522b220e94ab51da2b6dd53f","managedissuance":true,"version":0,"flags":{},"type":"Create Property - Manual","registered":false,"propertyid":31,"ismine":false,"propertyname":"TetherUS","confirmations":186115,"rdata":null,"data":"The next paradigm of money.","sendingaddress":"3MbYQMMmSkC3AgWkj9FMo5LsPTW1zBTwXL","type_int":54,"creationtxid":"5ed3694e8a4fa8d3ec5c75eb6789492c69e65511522b220e94ab51da2b6dd53f","name":"TetherUS","divisible":true,"ecosystem":"main","fixedissuance":false,"url":"https://tether.to","amount":"0.00000000","positioninblock":767,"propertytype":"divisible","valid":true,"block":324140}
         * id : 31
         * error : false
         */

        private String pendingpos;
        private String reserved;
        private boolean divisible;
        private String symbol;
        private String value;
        private String frozen;
        private String pendingneg;
        private PropertyinfoBean propertyinfo;
        private String id;
        private boolean error;

        public String getPendingpos() {
            return pendingpos;
        }

        public void setPendingpos(String pendingpos) {
            this.pendingpos = pendingpos;
        }

        public String getReserved() {
            return reserved;
        }

        public void setReserved(String reserved) {
            this.reserved = reserved;
        }

        public boolean isDivisible() {
            return divisible;
        }

        public void setDivisible(boolean divisible) {
            this.divisible = divisible;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getFrozen() {
            return frozen;
        }

        public void setFrozen(String frozen) {
            this.frozen = frozen;
        }

        public String getPendingneg() {
            return pendingneg;
        }

        public void setPendingneg(String pendingneg) {
            this.pendingneg = pendingneg;
        }

        public PropertyinfoBean getPropertyinfo() {
            return propertyinfo;
        }

        public void setPropertyinfo(PropertyinfoBean propertyinfo) {
            this.propertyinfo = propertyinfo;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isError() {
            return error;
        }

        public void setError(boolean error) {
            this.error = error;
        }

        public static class PropertyinfoBean {
            /**
             * subcategory : Activities auxiliary to financial service and insurance activities
             * blockhash : 00000000000000001e76250b3725547b5887329cfe3a8bb930a70e66747384d3
             * totaltokens : 2520000000.00000000
             * freezingenabled : true
             * issuer : 3MbYQMMmSkC3AgWkj9FMo5LsPTW1zBTwXL
             * category : Financial and insurance activities
             * fee : 0.00010000
             * blocktime : 1412613555
             * txid : 5ed3694e8a4fa8d3ec5c75eb6789492c69e65511522b220e94ab51da2b6dd53f
             * managedissuance : true
             * version : 0
             * flags : {}
             * type : Create Property - Manual
             * registered : false
             * propertyid : 31
             * ismine : false
             * propertyname : TetherUS
             * confirmations : 186115
             * rdata : null
             * data : The next paradigm of money.
             * sendingaddress : 3MbYQMMmSkC3AgWkj9FMo5LsPTW1zBTwXL
             * type_int : 54
             * creationtxid : 5ed3694e8a4fa8d3ec5c75eb6789492c69e65511522b220e94ab51da2b6dd53f
             * name : TetherUS
             * divisible : true
             * ecosystem : main
             * fixedissuance : false
             * url : https://tether.to
             * amount : 0.00000000
             * positioninblock : 767
             * propertytype : divisible
             * valid : true
             * block : 324140
             */

            private String subcategory;
            private String blockhash;
            private String totaltokens;
            private boolean freezingenabled;
            private String issuer;
            private String category;
            private String fee;
            private int blocktime;
            private String txid;
            private boolean managedissuance;
            private int version;
            private FlagsBean flags;
            private String type;
            private boolean registered;
            private int propertyid;
            private boolean ismine;
            private String propertyname;
            private int confirmations;
            private Object rdata;
            private String data;
            private String sendingaddress;
            private int type_int;
            private String creationtxid;
            private String name;
            private boolean divisible;
            private String ecosystem;
            private boolean fixedissuance;
            private String url;
            private String amount;
            private int positioninblock;
            private String propertytype;
            private boolean valid;
            private int block;

            public String getSubcategory() {
                return subcategory;
            }

            public void setSubcategory(String subcategory) {
                this.subcategory = subcategory;
            }

            public String getBlockhash() {
                return blockhash;
            }

            public void setBlockhash(String blockhash) {
                this.blockhash = blockhash;
            }

            public String getTotaltokens() {
                return totaltokens;
            }

            public void setTotaltokens(String totaltokens) {
                this.totaltokens = totaltokens;
            }

            public boolean isFreezingenabled() {
                return freezingenabled;
            }

            public void setFreezingenabled(boolean freezingenabled) {
                this.freezingenabled = freezingenabled;
            }

            public String getIssuer() {
                return issuer;
            }

            public void setIssuer(String issuer) {
                this.issuer = issuer;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getFee() {
                return fee;
            }

            public void setFee(String fee) {
                this.fee = fee;
            }

            public int getBlocktime() {
                return blocktime;
            }

            public void setBlocktime(int blocktime) {
                this.blocktime = blocktime;
            }

            public String getTxid() {
                return txid;
            }

            public void setTxid(String txid) {
                this.txid = txid;
            }

            public boolean isManagedissuance() {
                return managedissuance;
            }

            public void setManagedissuance(boolean managedissuance) {
                this.managedissuance = managedissuance;
            }

            public int getVersion() {
                return version;
            }

            public void setVersion(int version) {
                this.version = version;
            }

            public FlagsBean getFlags() {
                return flags;
            }

            public void setFlags(FlagsBean flags) {
                this.flags = flags;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public boolean isRegistered() {
                return registered;
            }

            public void setRegistered(boolean registered) {
                this.registered = registered;
            }

            public int getPropertyid() {
                return propertyid;
            }

            public void setPropertyid(int propertyid) {
                this.propertyid = propertyid;
            }

            public boolean isIsmine() {
                return ismine;
            }

            public void setIsmine(boolean ismine) {
                this.ismine = ismine;
            }

            public String getPropertyname() {
                return propertyname;
            }

            public void setPropertyname(String propertyname) {
                this.propertyname = propertyname;
            }

            public int getConfirmations() {
                return confirmations;
            }

            public void setConfirmations(int confirmations) {
                this.confirmations = confirmations;
            }

            public Object getRdata() {
                return rdata;
            }

            public void setRdata(Object rdata) {
                this.rdata = rdata;
            }

            public String getData() {
                return data;
            }

            public void setData(String data) {
                this.data = data;
            }

            public String getSendingaddress() {
                return sendingaddress;
            }

            public void setSendingaddress(String sendingaddress) {
                this.sendingaddress = sendingaddress;
            }

            public int getType_int() {
                return type_int;
            }

            public void setType_int(int type_int) {
                this.type_int = type_int;
            }

            public String getCreationtxid() {
                return creationtxid;
            }

            public void setCreationtxid(String creationtxid) {
                this.creationtxid = creationtxid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public boolean isDivisible() {
                return divisible;
            }

            public void setDivisible(boolean divisible) {
                this.divisible = divisible;
            }

            public String getEcosystem() {
                return ecosystem;
            }

            public void setEcosystem(String ecosystem) {
                this.ecosystem = ecosystem;
            }

            public boolean isFixedissuance() {
                return fixedissuance;
            }

            public void setFixedissuance(boolean fixedissuance) {
                this.fixedissuance = fixedissuance;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public int getPositioninblock() {
                return positioninblock;
            }

            public void setPositioninblock(int positioninblock) {
                this.positioninblock = positioninblock;
            }

            public String getPropertytype() {
                return propertytype;
            }

            public void setPropertytype(String propertytype) {
                this.propertytype = propertytype;
            }

            public boolean isValid() {
                return valid;
            }

            public void setValid(boolean valid) {
                this.valid = valid;
            }

            public int getBlock() {
                return block;
            }

            public void setBlock(int block) {
                this.block = block;
            }

            public static class FlagsBean {
            }
        }
    }
}
