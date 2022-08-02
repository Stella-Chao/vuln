'''Excel => MongoDB'''
import pandas as pd
from mongoUtils import connect_cwe
def transform():
    coll = connect_cwe()
    rc = pd.read_csv("../data/Research-Concepts.csv", index_col=False)
    hd = pd.read_csv("../data/Hardware-Design.csv", index_col=False)
    sd = pd.read_csv("../data/Software-Development.csv", index_col=False)
    len_rc = len(rc)
    len_hd = len(hd)
    len_sd = len(sd)
    for i in range(len_hd):
        cwe = {}
        cwe['cwe_id'] = str(hd['CWE-ID'][i])
        cwe['name'] = hd['Name'][i]
        cwe['status'] = hd['Status'][i]
        cwe['description'] = hd['Description'][i]
        cwe['ex_description'] = hd['Extended Description'][i]
        cwe['related_attack_pattern'] = hd['Related Attack Patterns'][i]
        cwe['type'] = "Hardware-Design"
        coll.insert_one(cwe)
    for i in range(len_rc):
        cwe = {}
        cwe['cwe_id'] = str(rc['CWE-ID'][i])
        cwe['name'] = rc['Name'][i]
        cwe['status'] = rc['Status'][i]
        cwe['description'] = rc['Description'][i]
        cwe['ex_description'] = rc['Extended Description'][i]
        cwe['related_attack_pattern'] = rc['Related Attack Patterns'][i]
        cwe['type'] = "Research-Concepts"
        coll.insert_one(cwe)
    for i in range(len_sd):
        cwe = {}
        cwe['cwe_id'] = str(sd['CWE-ID'][i])
        cwe['name'] = sd['Name'][i]
        cwe['status'] = sd['Status'][i]
        cwe['description'] = sd['Description'][i]
        cwe['ex_description'] = sd['Extended Description'][i]
        cwe['related_attack_pattern'] = sd['Related Attack Patterns'][i]
        cwe['type'] = "Software-Development"
        coll.insert_one(cwe)

if __name__ == '__main__':
    transform()