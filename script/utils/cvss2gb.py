

'''
CVSS2:(AV:N/AC:L/Au:N/C:N/I:P/A:N)

    ------影响度------
    C: 机密性影响    //(N:无,L:低,H:高)
    I: 完整性影响    //(N:无,L:低,H:高)
    A: 可用性影响    //(N:无,L:低,H:高)
    ------可利用度------
    AV: 攻击途径
    AC: 攻击复杂度
    Au: 认证         //(M:,S:,N:)
CVSS:3.0/AV:N/AC:L/PR:N/UI:N/S:U/C:N/I:L/A:N


CVSS:3.1/AV:N/AC:L/PR:L/UI:R/S:C/C:L/I:L/A:N

    ------影响度------
    C: 机密性影响    //(N:无,L:低,H:高)
    I: 完整性影响    //(N:无,L:低,H:高)
    A: 可用性影响    //(N:无,L:低,H:高)
    ------可利用度------
    AV: 攻击途径     //(N:远程网络,A:相邻网络,L:本地,P:物理)
    AC: 攻击复杂度   //(L:低,H:高)
    PR: 权限要求     //(N:无,L:低,H:高)
    UI: 用户交互     //(N:无需求,R:有需求)
'''

def cvss3_to_gb(vec):
    pass



def func():
    # 获取 CVSS3_Vector 字段： (AV:N/AC:L/Au:S/C:P/I:N/A:N)
    sql = """update nvd set """

    # 转换



    pass








if __name__ == '__main__':
    cvss3_to_gb()