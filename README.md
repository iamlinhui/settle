![还款计划](exp.png)

| 变量名 | 变量符号  | 
|-----|-------|
| LA  | 借款金额  | 
| YR  | 年利率   | 
| MYR | 罚息年利率 | 
| DRP | 日利率精度 | 
| MRP | 月利率精度 | 
| MA  | PMT月供 | 
| DR  | 日利率   | 
| RP  | 结果精度  |
| NPA | 未还本金  |

 基础配置信息（精度 + 利率）

```json
{
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
}
```

 还款计划配置信息 （等额本息）

```json
{
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
}
```

 还款计划配置信息 （等额本金）

```json
{
  "firstInterestRule": {
    "firstInterestAmountRule": "LA.mul(DAYS_FUNCTION(FSD,FRD).mul(DR),RP)",
    "firstPrincipalAmountRule": "MA",
    "firstRepayDateRule": "FRD_FUNCTION(RDN,BDN,PD)",
    "firstStartDateRule": "PD",
    "firstTotalAmountRule": "FPA.add(FIA)",
    "monthAmountRule": "LA.div(LM,RP)",
    "notPrincipalAmountRule": "LA.sub(FPA)"
  },
  "middleInterestRule": {
    "middleInterestAmountRule": "NPA.mul(MR,RP)",
    "middlePrincipalAmountRule": "MA",
    "middleRepayDateRule": "MONTH_ADD_FUNCTION(FRD,CLM.sub(1))",
    "middleTotalAmountRule": "MPA.add(MIA)",
    "monthAmountRule": "MA",
    "notPrincipalAmountRule": "NPA.sub(MPA)"
  },
  "lastInterestRule": {
    "lastInterestAmountRule": "NPA.mul(MR,RP)",
    "lastPrincipalAmountRule": "NPA",
    "lastRepayDateRule": "MONTH_ADD_FUNCTION(FRD,LM.sub(1))",
    "lastTotalAmountRule": "LPA.add(LIA)",
    "monthAmountRule": "MA",
    "notPrincipalAmountRule": "NPA.sub(LPA)"
  }
}
```

 实际还款配置信息

```json
{
  "allCompensateRule": {
    "basePayoffRule": {
      "onRepayDatePayoffRule": {
        "nextRealRepayDateRule": "DAY_ADD_FUNCTION(NOW,1)",
        "nextRealRepayInterestAmountRule": "NENA.mul(DR).mul(DAYS_FUNCTION(ESD,NOW).add(1),RP)",
        "nextRealRepayPrincipalAmountRule": "NEPA",
        "nextRealRepayTypeRule": "ART",
        "nextRealSettleDateRule": "DAY_ADD_FUNCTION(NOW,1)",
        "realRepayPrincipalAmountRule": "EPA"
      },
      "payoffRule": {
        "nextRealRepayPrincipalAmountRule": "NEPA",
        "realRepayDateRule": "DAY_ADD_FUNCTION(NOW,1)",
        "realRepayInterestAmountRule": "EIA",
        "realRepayPrincipalAmountRule": "EPA",
        "realRepayTypeRule": "NRT",
        "realSettleDateRule": "DAY_ADD_FUNCTION(NOW,1)"
      }
    },
    "singleCompensateRule": {
      "overdueRule": {
        "realRepayDateRule": "DAY_ADD_FUNCTION(NOW,1)",
        "realRepayInterestAmountRule": "EIA",
        "realRepayMulctAmountRule": "DAYS_FUNCTION(ERD,NOW).add(1) > 3 ? EPA.mul(MDR).mul(DAYS_FUNCTION(ERD,NOW).add(1),RR).min(EPA) : 0",
        "realRepayPrincipalAmountRule": "EPA",
        "realRepayTypeRule": "DAYS_FUNCTION(ERD,NOW).add(1) > 3 ? ORT : NRT",
        "realSettleDateRule": "DAY_ADD_FUNCTION(NOW,1)"
      }
    }
  },
  "basePayoffRule": {
    "onRepayDatePayoffRule": {
      "nextRealRepayDateRule": "DAY_ADD_FUNCTION(NOW,1)",
      "nextRealRepayInterestAmountRule": "NENA.mul(DR).mul(DAYS_FUNCTION(ESD,NOW).add(1),RP)",
      "nextRealRepayPrincipalAmountRule": "NEPA",
      "nextRealRepayTypeRule": "ART",
      "nextRealSettleDateRule": "DAY_ADD_FUNCTION(NOW,1)",
      "realRepayPrincipalAmountRule": "EPA"
    },
    "payoffRule": {
      "nextRealRepayPrincipalAmountRule": "NEPA",
      "realRepayDateRule": "DAY_ADD_FUNCTION(NOW,1)",
      "realRepayInterestAmountRule": "EIA",
      "realRepayPrincipalAmountRule": "EPA",
      "realRepayTypeRule": "NRT",
      "realSettleDateRule": "DAY_ADD_FUNCTION(NOW,1)"
    }
  },
  "normalRule": {
    "realRepayDateRule": "DAY_ADD_FUNCTION(ERD,1)",
    "realRepayInterestAmountRule": "EIA",
    "realRepayPrincipalAmountRule": "EPA",
    "realRepayTypeRule": "NRT",
    "realSettleDateRule": "ERD"
  },
  "overdueRule": {
    "realRepayDateRule": "DAY_ADD_FUNCTION(NOW,1)",
    "realRepayInterestAmountRule": "EIA",
    "realRepayMulctAmountRule": "DAYS_FUNCTION(ERD,NOW).add(1) > 3 ? EPA.mul(MDR).mul(DAYS_FUNCTION(ERD,NOW).add(1),RR).min(EPA) : 0",
    "realRepayPrincipalAmountRule": "EPA",
    "realRepayTypeRule": "DAYS_FUNCTION(ERD,NOW).add(1) > 3 ? ORT : NRT",
    "realSettleDateRule": "DAY_ADD_FUNCTION(NOW,1)"
  },
  "singleCompensateRule": {
    "overdueRule": {
      "realRepayDateRule": "DAY_ADD_FUNCTION(NOW,1)",
      "realRepayInterestAmountRule": "EIA",
      "realRepayMulctAmountRule": "DAYS_FUNCTION(ERD,NOW).add(1) > 3 ? EPA.mul(MDR).mul(DAYS_FUNCTION(ERD,NOW).add(1),RR).min(EPA) : 0",
      "realRepayPrincipalAmountRule": "EPA",
      "realRepayTypeRule": "DAYS_FUNCTION(ERD,NOW).add(1) > 3 ? ORT : NRT",
      "realSettleDateRule": "DAY_ADD_FUNCTION(NOW,1)"
    }
  }
}
```