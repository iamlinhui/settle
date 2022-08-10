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
                    "dayRatePrecisionRule": "half_up(12)",
                    "monthRatePrecisionRule": "half_up(12)",
                    "processPrecisionRule": "half_up(8)",
                    "resultPrecisionRule": "half_up(2)"
                },
                "rateInfoRule": {
                    "dayOfMulctRateRule": "MYR.div(360,DRP)",
                    "dayOfYearRateRule": "YR.div(360,DRP)",
                    "monthOfMulctRateRule": "MYR.div(12,MRP)",
                    "monthOfYearRateRule": "YR.div(12,MRP)",
                    "mulctRateRule": "YR.mul(1.5)",
                    "yearRateRule": "0.08"
                }
            },
            interest: {
                1: {
                    "firstInterestRule": {
                        "firstInterestAmountRule": "LA.mul(DAYS_FUNCTION(FSD,FRD).mul(DR),RP)",
                        "firstPrincipalAmountRule": "MA.sub(LA.mul(MR),RP)",
                        "firstRepayDateRule": "FRD_FUNCTION(RDN,BDN,PD)",
                        "firstStartDateRule": "PD",
                        "firstTotalAmountRule": "FPA.add(FIA)",
                        "monthAmountRule": "LA.mul(MR).mul(MR.add(1).pow(LM)).div(MR.add(1).pow(LM).sub(1),RP)"
                    },
                    "middleInterestRule": {
                        "middleInterestAmountRule": "NPA.mul(DAYS_FUNCTION(PRD,MRD).mul(DR),RP)",
                        "middlePrincipalAmountRule": "NPA > 0 ? MA.sub(MIA,RP) : NPA",
                        "middleRepayDateRule": "MONTH_ADD_FUNCTION(FRD,CLM.sub(1))",
                        "middleTotalAmountRule": "MPA.add(MIA)",
                        "monthAmountRule": "MA"
                    },
                    "lastInterestRule": {
                        "lastInterestAmountRule": "NPA.mul(DAYS_FUNCTION(PRD,LRD).mul(DR),RP)",
                        "lastPrincipalAmountRule": "NPA",
                        "lastRepayDateRule": "MONTH_ADD_FUNCTION(FRD,LM.sub(1))",
                        "lastTotalAmountRule": "LPA.add(LIA)",
                        "monthAmountRule": "MA"
                    }
                },
                2: {
                    "firstInterestRule": {
                        "firstInterestAmountRule": "LA.mul(DAYS_FUNCTION(FSD,FRD).mul(DR),RP)",
                        "firstPrincipalAmountRule": "MA.sub(FIA,RP)",
                        "firstRepayDateRule": "FRD_FUNCTION(RDN,BDN,PD)",
                        "firstStartDateRule": "PD",
                        "firstTotalAmountRule": "FPA.add(FIA)",
                        "monthAmountRule": "LA.mul(MR).mul(MR.add(1).pow(LM)).div(MR.add(1).pow(LM).sub(1),RP)"
                    },
                    "middleInterestRule": {
                        "middleInterestAmountRule": "NPA.mul(DAYS_FUNCTION(PRD,MRD).mul(DR),RP)",
                        "middlePrincipalAmountRule": "NPA > 0 ? MA.sub(MIA,RP) : NPA",
                        "middleRepayDateRule": "MONTH_ADD_FUNCTION(FRD,CLM.sub(1))",
                        "middleTotalAmountRule": "MPA.add(MIA)",
                        "monthAmountRule": "MA"
                    },
                    "lastInterestRule": {
                        "lastInterestAmountRule": "NPA.mul(DAYS_FUNCTION(PRD,LRD).mul(DR),RP)",
                        "lastPrincipalAmountRule": "NPA",
                        "lastRepayDateRule": "MONTH_ADD_FUNCTION(FRD,LM.sub(1))",
                        "lastTotalAmountRule": "LPA.add(LIA)",
                        "monthAmountRule": "MA"
                    }

                },
                3: {
                    "firstInterestRule": {
                        "firstInterestAmountRule": "LA.mul(DAYS_FUNCTION(FSD,FRD).mul(DR),RP)",
                        "firstPrincipalAmountRule": "MA.sub(LA.mul(MR),RP)",
                        "firstRepayDateRule": "FRD_FUNCTION(RDN,BDN,PD)",
                        "firstStartDateRule": "PD",
                        "firstTotalAmountRule": "FPA.add(FIA)",
                        "monthAmountRule": "LA.mul(MR).mul(MR.add(1).pow(LM)).div(MR.add(1).pow(LM).sub(1),RP)"
                    },
                    "middleInterestRule": {
                        "middleInterestAmountRule": "NPA.mul(MR,RP)",
                        "middlePrincipalAmountRule": "NPA > 0 ? MA.sub(NPA.mul(MR),RP) : NPA",
                        "middleRepayDateRule": "MONTH_ADD_FUNCTION(FRD,CLM.sub(1))",
                        "middleTotalAmountRule": "MPA.add(MIA)",
                        "monthAmountRule": "MA"
                    },
                    "lastInterestRule": {
                        "lastInterestAmountRule": "NPA.mul(MR,RP)",
                        "lastPrincipalAmountRule": "NPA",
                        "lastRepayDateRule": "MONTH_ADD_FUNCTION(FRD,LM.sub(1))",
                        "lastTotalAmountRule": "LPA.add(LIA)",
                        "monthAmountRule": "MA"
                    }
                },
                4: {
                    "firstInterestRule": {
                        "firstInterestAmountRule": "LA.mul(DAYS_FUNCTION(FSD,FRD).mul(DR),RP)",
                        "firstPrincipalAmountRule": "MA.sub(FIA,RP)",
                        "firstRepayDateRule": "FRD_FUNCTION(RDN,BDN,PD)",
                        "firstStartDateRule": "PD",
                        "firstTotalAmountRule": "FPA.add(FIA)",
                        "monthAmountRule": "LA.mul(MR).mul(MR.add(1).pow(LM)).div(MR.add(1).pow(LM).sub(1),RP)"
                    },
                    "middleInterestRule": {
                        "middleInterestAmountRule": "NPA.mul(MR,RP)",
                        "middlePrincipalAmountRule": "NPA > 0 ? MA.sub(NPA.mul(MR),RP) : NPA",
                        "middleRepayDateRule": "MONTH_ADD_FUNCTION(FRD,CLM.sub(1))",
                        "middleTotalAmountRule": "MPA.add(MIA)",
                        "monthAmountRule": "MA"
                    },
                    "lastInterestRule": {
                        "lastInterestAmountRule": "NPA.mul(MR,RP)",
                        "lastPrincipalAmountRule": "NPA",
                        "lastRepayDateRule": "MONTH_ADD_FUNCTION(FRD,LM.sub(1))",
                        "lastTotalAmountRule": "LPA.add(LIA)",
                        "monthAmountRule": "MA"
                    }

                },
                5: {
                    "firstInterestRule": {
                        "firstInterestAmountRule": "LA.mul(DAYS_FUNCTION(FSD,FRD).mul(DR),RP)",
                        "firstPrincipalAmountRule": "MA",
                        "firstRepayDateRule": "FRD_FUNCTION(RDN,BDN,PD)",
                        "firstStartDateRule": "PD",
                        "firstTotalAmountRule": "FPA.add(FIA)",
                        "monthAmountRule": "LA.div(LM,RP)"
                    },
                    "middleInterestRule": {
                        "middleInterestAmountRule": "NPA.mul(MR,RP)",
                        "middlePrincipalAmountRule": "MA",
                        "middleRepayDateRule": "MONTH_ADD_FUNCTION(FRD,CLM.sub(1))",
                        "middleTotalAmountRule": "MPA.add(MIA)",
                        "monthAmountRule": "MA"
                    },
                    "lastInterestRule": {
                        "lastInterestAmountRule": "NPA.mul(MR,RP)",
                        "lastPrincipalAmountRule": "NPA",
                        "lastRepayDateRule": "MONTH_ADD_FUNCTION(FRD,LM.sub(1))",
                        "lastTotalAmountRule": "LPA.add(LIA)",
                        "monthAmountRule": "MA"
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
                // layer.msg(error, {icon: 2});
                this.show = false
                layer.close(index);
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