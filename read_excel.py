import pandas as pd
import sys

try:
    xl = pd.ExcelFile('test-cases/testCases.xlsx')
    print('Available sheets:', xl.sheet_names)
    
    for sheet in xl.sheet_names:
        print(f'\n=== Sheet: {sheet} ===')
        df = pd.read_excel('test-cases/testCases.xlsx', sheet_name=sheet)
        print('Columns:', df.columns.tolist())
        print('Shape:', df.shape)
        print('First 10 rows:')
        print(df.head(10).to_string())
        print()
        
except Exception as e:
    print('Error reading Excel file:', e)
    sys.exit(1)
