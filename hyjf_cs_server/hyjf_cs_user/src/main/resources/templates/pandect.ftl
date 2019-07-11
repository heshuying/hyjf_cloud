{
<#if datas??>
  "status": "${datas.status!''}",
  "statusDesc": "${datas.statusDesc!''}",
  "data": {
        "pointsShopSwitch":${(datas.data.pointsShopSwitch)!'0'},
        "bankCard":${(datas.data.bankCard)!'0'},
        "isVip":${(datas.data.isVip)?string('true', 'false')},
        "vipName":"${(datas.data.vipName)!''}",
        "couponValidCount":"${(datas.data.couponValidCount)!''}",
        "bankOpenAccount": {
                <#if datas.data.bankOpenAccount??>
                    <#assign bankOpenAccount = datas.data.bankOpenAccount >
                </#if>
               "account": "${(bankOpenAccount.account)!''}"
        },
       "userEvaluationResultFlag": "${(datas.data.userEvaluationResultFlag)!''}",
        "auth": "${(datas.data.auth)!''}",
        "webViewUser": {
                <#if datas.data.webViewUser??>
                    <#assign webViewUser = datas.data.webViewUser >
                </#if>
                "email": "${(webViewUser.email)!''}",
                <#if webViewUser.chinapnrUsrcustid??>
                    "chinapnrUsrid":"${(webViewUser.chinapnrUsrcustid)?c!''}",
                </#if>
                "bankOpenAccount": ${(webViewUser.bankOpenAccount)?string('1', '0')}

        },
        "user": {
                    <#if datas.data.user??>
                        <#assign user = datas.data.user >
                    </#if>
                  "iconUrl": "${user.iconUrl!''}",
                  "isSetPassword": "${user.isSetPassword!'0'}",
                  "pointsCurrent": "${user.pointsCurrent!'0'}"
        },
         "account": {
                    <#if datas.data.account??>
                        <#assign account = datas.data.account >
                  "balance": ${(account.balance)?c!'0'},
                  "planAccountWait": ${(account.planAccountWait!0)?c!'0'},
                  "planCapitalWait": ${(account.planCapitalWait!0)?c},
                  "planInterestWait": ${(account.planInterestWait!0)?c!'0'},
                  "bankTotal": ${(account.bankTotal!0)?c},
                  "bankBalance": ${(account.bankBalance!0)?c!'0'},
                  "bankFrost": ${(account.bankFrost!0)?c!'0'},
                  "bankInterestSum": ${(account.bankInterestSum!0)?c!'0'},
                  "bankAwait": ${(account.bankAwait!0)?c!'0'},
                  "bankAwaitCapital": ${(account.bankAwaitCapital!0)?c!'0'},
                  "bankAwaitInterest": ${(account.bankAwaitInterest!0)?c!'0'}
                    <#else >
                  "balance":0,
                  "planAccountWait":0,
                  "planCapitalWait":0,
                  "planInterestWait":0,
                  "bankTotal":0,
                  "bankBalance":0,
                  "bankFrost":0,
                  "bankInterestSum":0,
                  "bankAwait":0,
                  "bankAwaitCapital":0,
                  "bankAwaitInterest":0
                    </#if>

        },
        "userName": "${datas.data.username!''}",
        "recoverLatestList": [
                    <#if datas.data.recoverLatestList??>
                        <#assign recoverLatestList = datas.data.recoverLatestList >
                        <#list recoverLatestList as recoverLatest>
                      {
                        "investDate":"${(recoverLatest.investDate)!''}",
                        "projectNid":"${(recoverLatest.projectNid)!''}",
                        "projectName":"${(recoverLatest.projectName)!''}",
                        "type":"${(recoverLatest.type)!''}",
                        "couponType":"${(recoverLatest.couponType)!''}",
                        "borrowApr":"${(recoverLatest.borrowApr)!''}",
                        "totalWait":"${(recoverLatest.totalWait)!''}",
                        "exitType":"${(recoverLatest.exitType)!''}",
                        "hjhType":"${(recoverLatest.hjhType)!''}"
                    }
                            <#if recoverLatest_has_next>,</#if>
                        </#list>
                    </#if>
        ]
  }
</#if>
 }