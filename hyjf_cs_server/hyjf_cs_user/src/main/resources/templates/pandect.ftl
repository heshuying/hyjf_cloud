{
<#if datas??>
  "status": "${datas.status!''}",
  "statusDesc": "${datas.statusDesc!''}",
  "data": {
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
                "bankOpenAccount": ${(webViewUser.bankOpenAccount)!'0'},
                "chinapnrUsrid":"${(webViewUser.chinapnrUsrid)!''}"
        },
        "user": {
                <#if datas.data.user??>
                    <#assign user = datas.data.user >
                </#if>
                  "iconUrl": "${user.iconUrl!''}",
                  "isSetPassword": "${user.isSetPassword!'0'}"
        },
         "account": {
                    <#if datas.data.account??>
                        <#assign account = datas.data.account >
                    </#if>
                  "balance": ${(account.balance)?c!''},
                  "planAccountWait": ${(account.planAccountWait)?c!'0'},
                  "planCapitalWait": ${(account.planCapitalWait)?c!'0'},
                  "planInterestWait": ${(account.planInterestWait)?c!'0'},
                  "bankTotal": ${(account.bankTotal)?c!'0'},
                  "bankBalance": ${(account.bankBalance)?c!'0'},
                  "bankFrost": ${(account.bankFrost)?c!'0'},
                  "bankInterestSum": ${(account.bankInterestSum)?c!'0'},
                  "bankAwait": ${(account.bankAwait)?c!'0'},
                  "bankAwaitCapital": ${(account.bankAwaitCapital)?c!'0'},
                  "bankAwaitInterest": ${(account.bankAwaitInterest)?c!'0'}
        },
        "userName": "${datas.data.userName!''}",
        "recoverLatestList": [
                <#if datas.data.recoverLatestList??>
                    <#assign recoverLatestList = datas.data.recoverLatestList >
                </#if>
                <#list recoverLatestList as recoverLatest>
                      {
                        "investDate":"${(recoverLatest.investDate)!''}",
                        "projectName":"${(recoverLatest.projectName)!''}",
                        "type":"${(recoverLatest.type)!''}",
                        "couponType":"${(recoverLatest.couponType)!''}",
                        "borrowApr":"${(recoverLatest.borrowApr)!''}",
                        "totalWait":"${(recoverLatest.totalWait)!''}"
                    }
                        <#if recoverLatest_has_next>,</#if>
                </#list>
        ]
  }
</#if>
 }