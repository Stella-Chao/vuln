from utils import sql
import pandas as pd

def db_to_excel():
    s = 'select * from nvd'
    cursor = sql.connect()
    cursor.execute(s)
    data = cursor.fetchall()
    # pd.read_excel('nvd.xlsx')
    columnDes = cursor.description
    columnNames = [columnDes[i][0] for i in range(len(columnDes))]
    df = pd.DataFrame([list(i) for i in data], columns=columnNames)
    cursor.close()
    df.to_excel('nvd.xlsx')


if __name__ == '__main__':
    db_to_excel()