new Vue({
    el: '.main',
    data() {
        return {
            show: false,
            choose: 1,
            allPrincipal: 0,
            allFee: 0,
            allTotal: 0,
            result: [{
                "repayRate": "",
                "mulctRate": "",
                "repayTerm": "",
                "repayDate": "",
                "repayTotal": "",
                "repayPrincipal": "",
                "repayFee": "",
                "notRepayPrincipal": "",
                "betweenDays": ""
            }],
            payOrder: {
                loanTerm: 36,
                loanAmount: 10000,
                paymentTime: new Date().toISOString().split('T')[0],
                fixedRepayDay: 14,
                fixedBillDay: 4
            },
            baseInfo: {
                "precisionInfoRule": {
                    "dayRatePrecisionRule": "@precision.init(12,'HALF_UP')",
                    "monthRatePrecisionRule": "@precision.init(12,'HALF_UP')",
                    "processPrecisionRule": "@precision.init(8,'HALF_UP')",
                    "resultPrecisionRule": "@precision.init(2,'HALF_UP')"
                },
                "rateInfoRule": {
                    "dayOfMulctRateRule": "#MYR.divide(360,#DRP)",
                    "dayOfYearRateRule": "#YR.divide(360,#DRP)",
                    "monthOfMulctRateRule": "#MYR.divide(12,#MRP)",
                    "monthOfYearRateRule": "#YR.divide(12,#MRP)",
                    "mulctRateRule": "#YR.multiply(1.5)",
                    "yearRateRule": "0.08"
                }
            },
            interest: {
                1: {
                    "firstInterestRule": {
                        "firstInterestAmountRule": "#LA.multiply(#DAYS_FUNCTION(#FSD,#FRD).multiply(#DR),#RP)",
                        "firstPrincipalAmountRule": "#MA.subtract(#LA.multiply(#MR),#RP)",
                        "firstRepayDateRule": "#FRD_FUNCTION(#RDN,#BDN,#PD)",
                        "firstStartDateRule": "#PD",
                        "firstTotalAmountRule": "#FPA.add(#FIA)",
                        "monthAmountRule": "#LA.multiply(#MR).multiply(#MR.add(1).pow(#LM)).divide(#MR.add(1).pow(#LM).subtract(1),#RP)"
                    },
                    "middleInterestRule": {
                        "middleInterestAmountRule": "#NPA.multiply(#DAYS_FUNCTION(#PRD,#MRD).multiply(#DR),#RP)",
                        "middlePrincipalAmountRule": "#NPA > 0 ? #MA.subtract(#MIA,#RP) : #NPA",
                        "middleRepayDateRule": "#MONTH_ADD_FUNCTION(#FRD,#CLM.subtract(1))",
                        "middleTotalAmountRule": "#MPA.add(#MIA)",
                        "monthAmountRule": "#MA"
                    },
                    "lastInterestRule": {
                        "lastInterestAmountRule": "#NPA.multiply(#DAYS_FUNCTION(#PRD,#LRD).multiply(#DR),#RP)",
                        "lastPrincipalAmountRule": "#NPA",
                        "lastRepayDateRule": "#MONTH_ADD_FUNCTION(#FRD,#LM.subtract(1))",
                        "lastTotalAmountRule": "#LPA.add(#LIA)",
                        "monthAmountRule": "#MA"
                    }
                },
                2: {
                    "firstInterestRule": {
                        "firstInterestAmountRule": "#LA.multiply(#DAYS_FUNCTION(#FSD,#FRD).multiply(#DR),#RP)",
                        "firstPrincipalAmountRule": "#MA.subtract(#FIA,#RP)",
                        "firstRepayDateRule": "#FRD_FUNCTION(#RDN,#BDN,#PD)",
                        "firstStartDateRule": "#PD",
                        "firstTotalAmountRule": "#FPA.add(#FIA)",
                        "monthAmountRule": "#LA.multiply(#MR).multiply(#MR.add(1).pow(#LM)).divide(#MR.add(1).pow(#LM).subtract(1),#RP)"
                    },
                    "middleInterestRule": {
                        "middleInterestAmountRule": "#NPA.multiply(#DAYS_FUNCTION(#PRD,#MRD).multiply(#DR),#RP)",
                        "middlePrincipalAmountRule": "#NPA > 0 ? #MA.subtract(#MIA,#RP) : #NPA",
                        "middleRepayDateRule": "#MONTH_ADD_FUNCTION(#FRD,#CLM.subtract(1))",
                        "middleTotalAmountRule": "#MPA.add(#MIA)",
                        "monthAmountRule": "#MA"
                    },
                    "lastInterestRule": {
                        "lastInterestAmountRule": "#NPA.multiply(#DAYS_FUNCTION(#PRD,#LRD).multiply(#DR),#RP)",
                        "lastPrincipalAmountRule": "#NPA",
                        "lastRepayDateRule": "#MONTH_ADD_FUNCTION(#FRD,#LM.subtract(1))",
                        "lastTotalAmountRule": "#LPA.add(#LIA)",
                        "monthAmountRule": "#MA"
                    }

                },
                3: {
                    "firstInterestRule": {
                        "firstInterestAmountRule": "#LA.multiply(#DAYS_FUNCTION(#FSD,#FRD).multiply(#DR),#RP)",
                        "firstPrincipalAmountRule": "#MA.subtract(#LA.multiply(#MR),#RP)",
                        "firstRepayDateRule": "#FRD_FUNCTION(#RDN,#BDN,#PD)",
                        "firstStartDateRule": "#PD",
                        "firstTotalAmountRule": "#FPA.add(#FIA)",
                        "monthAmountRule": "#LA.multiply(#MR).multiply(#MR.add(1).pow(#LM)).divide(#MR.add(1).pow(#LM).subtract(1),#RP)"
                    },
                    "middleInterestRule": {
                        "middleInterestAmountRule": "#NPA.multiply(#MR,#RP)",
                        "middlePrincipalAmountRule": "#NPA > 0 ? #MA.subtract(#NPA.multiply(#MR),#RP) : #NPA",
                        "middleRepayDateRule": "#MONTH_ADD_FUNCTION(#FRD,#CLM.subtract(1))",
                        "middleTotalAmountRule": "#MPA.add(#MIA)",
                        "monthAmountRule": "#MA"
                    },
                    "lastInterestRule": {
                        "lastInterestAmountRule": "#NPA.multiply(#MR,#RP)",
                        "lastPrincipalAmountRule": "#NPA",
                        "lastRepayDateRule": "#MONTH_ADD_FUNCTION(#FRD,#LM.subtract(1))",
                        "lastTotalAmountRule": "#LPA.add(#LIA)",
                        "monthAmountRule": "#MA"
                    }
                },
                4: {
                    "firstInterestRule": {
                        "firstInterestAmountRule": "#LA.multiply(#DAYS_FUNCTION(#FSD,#FRD).multiply(#DR),#RP)",
                        "firstPrincipalAmountRule": "#MA.subtract(#FIA,#RP)",
                        "firstRepayDateRule": "#FRD_FUNCTION(#RDN,#BDN,#PD)",
                        "firstStartDateRule": "#PD",
                        "firstTotalAmountRule": "#FPA.add(#FIA)",
                        "monthAmountRule": "#LA.multiply(#MR).multiply(#MR.add(1).pow(#LM)).divide(#MR.add(1).pow(#LM).subtract(1),#RP)"
                    },
                    "middleInterestRule": {
                        "middleInterestAmountRule": "#NPA.multiply(#MR,#RP)",
                        "middlePrincipalAmountRule": "#NPA > 0 ? #MA.subtract(#NPA.multiply(#MR),#RP) : #NPA",
                        "middleRepayDateRule": "#MONTH_ADD_FUNCTION(#FRD,#CLM.subtract(1))",
                        "middleTotalAmountRule": "#MPA.add(#MIA)",
                        "monthAmountRule": "#MA"
                    },
                    "lastInterestRule": {
                        "lastInterestAmountRule": "#NPA.multiply(#MR,#RP)",
                        "lastPrincipalAmountRule": "#NPA",
                        "lastRepayDateRule": "#MONTH_ADD_FUNCTION(#FRD,#LM.subtract(1))",
                        "lastTotalAmountRule": "#LPA.add(#LIA)",
                        "monthAmountRule": "#MA"
                    }

                },
                5: {
                    "firstInterestRule": {
                        "firstInterestAmountRule": "#LA.multiply(#DAYS_FUNCTION(#FSD,#FRD).multiply(#DR),#RP)",
                        "firstPrincipalAmountRule": "#MA",
                        "firstRepayDateRule": "#FRD_FUNCTION(#RDN,#BDN,#PD)",
                        "firstStartDateRule": "#PD",
                        "firstTotalAmountRule": "#FPA.add(#FIA)",
                        "monthAmountRule": "#LA.divide(#LM,#RP)"
                    },
                    "middleInterestRule": {
                        "middleInterestAmountRule": "#NPA.multiply(#MR,#RP)",
                        "middlePrincipalAmountRule": "#MA",
                        "middleRepayDateRule": "#MONTH_ADD_FUNCTION(#FRD,#CLM.subtract(1))",
                        "middleTotalAmountRule": "#MPA.add(#MIA)",
                        "monthAmountRule": "#MA"
                    },
                    "lastInterestRule": {
                        "lastInterestAmountRule": "#NPA.multiply(#MR,#RP)",
                        "lastPrincipalAmountRule": "#NPA",
                        "lastRepayDateRule": "#MONTH_ADD_FUNCTION(#FRD,#LM.subtract(1))",
                        "lastTotalAmountRule": "#LPA.add(#LIA)",
                        "monthAmountRule": "#MA"
                    }
                }
            }
        }
    },
    methods: {
        settle() {

            this.payOrder.loanTerm = parseInt(this.payOrder.loanTerm);
            this.payOrder.fixedBillDay = parseInt(this.payOrder.fixedBillDay);
            this.payOrder.fixedRepayDay = parseInt(this.payOrder.fixedRepayDay);

            if (this.payOrder.loanTerm <= 0 || this.payOrder.loanTerm > 360) {
                layer.msg('借款期数参数异常', {time: 1000, icon: 2})
                return;
            }
            if (this.payOrder.loanAmount <= 0) {
                layer.msg('借款金额参数异常', {time: 1000, icon: 2})
                return;
            }

            if (this.payOrder.fixedBillDay <= 0 || this.payOrder.fixedBillDay > 31) {
                layer.msg('固定账单日参数异常', {time: 1000, icon: 2})
                return;
            }
            if (this.payOrder.fixedRepayDay <= 0 || this.payOrder.fixedRepayDay > 31) {
                layer.msg('固定还款日参数异常', {time: 1000, icon: 2})
                return;
            }

            if (this.baseInfo.rateInfoRule.yearRateRule <= 0) {
                layer.msg('年化利率参数异常', {time: 1000, icon: 2})
                return;
            }

            if (!this.payOrder.paymentTime.match(/^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/)) {
                layer.msg('放款日期参数异常', {time: 1000, icon: 2})
                return;
            }
            const index = layer.load(1);
            axios.post('/settle', {
                payOrder: this.payOrder,
                rule: {
                    baseInfoRule: this.baseInfo,
                    baseInterestRule: this.interest[this.choose]
                }
            }).then((response) => {
                this.result = response.data
                this.show = true
                layer.close(index);
            }).catch((error) => {
                console.log(error);
                this.show = false
                this.close(index);
            })
        }
    },
    watch: {
        'result': {
            handler(val) {
                this.allPrincipal = 0
                this.allFee = 0
                this.allTotal = 0
                this.result.forEach(item => {
                    this.allPrincipal += Number(item.repayPrincipal)
                    this.allFee += Number(item.repayFee)
                    this.allTotal += Number(item.repayTotal)
                })
                this.allPrincipal = this.allPrincipal.toFixed(2)
                this.allFee = this.allFee.toFixed(2)
                this.allTotal = this.allTotal.toFixed(2)
            }
        }
    }
});