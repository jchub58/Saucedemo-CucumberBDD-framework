import pandas as pd

try:
    df = pd.read_excel('test-cases/testCases.xlsx')
    print('Total test cases:', len(df))
    print('\nAll test cases:')
    for i, row in df.iterrows():
        print(f'{i+1}. ID: {row["Test Case ID"]}')
        print(f'   Module: {row["Module"]}')
        print(f'   Scenario: {row["Test Scenario"]}')
        print(f'   Steps: {row["Test Steps"]}')
        print(f'   Data: {row["Test Data"]}')
        print(f'   Expected: {row["Expected Result"]}')
        print(f'   Priority: {row["Priority"]}')
        print('---')
        
except Exception as e:
    print('Error:', e)
