import pyodbc as pyodbc

DRIVER_NAME = 'SQL SERVER'
SERVER_NAME = 'DESKTOP-8KEOEI7\\SQLEXPRESS'
DB_NAME = 'CyCalFX23'

conn = pyodbc.connect('Driver=' + DRIVER_NAME + ';'
                      'Server=' + SERVER_NAME + ';'
                      'Database=' + DB_NAME + ';'
                      'Trusted_Connection=yes;')


def insert_race(race):
    c = conn.cursor()
    c.execute("""INSERT INTO [race] 
                 VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""", race)
    conn.commit()


def insert_races(conn, data):
    table = 'race'
    columns = ['id', 'category_id', 'name', 'start_date', 'end_date', 'logo', 'flag', 'distance', 'profile_icon',
               'profile']

    cursor = conn.cursor()
    columns_str = ', '.join(columns)
    placeholders = ', '.join('?' * len(columns))
    sql = f'INSERT INTO {table} ({columns_str}) VALUES ({placeholders})'

    cursor.executemany(sql, data)
    conn.commit()


def insert_stages(conn, data):
    table = 'race'
    columns = ['id', 'race_id', 'number', 'date', 'distance', 'profile_icon', 'profile']

    cursor = conn.cursor()
    columns_str = ', '.join(columns)
    placeholders = ', '.join('?' * len(columns))
    sql = f'INSERT INTO {table} ({columns_str}) VALUES ({placeholders})'

    cursor.executemany(sql, data)
    conn.commit()


def insert_stage(stage):
    c = conn.cursor()
    c.execute("""INSERT INTO [race] 
                 VALUES (?, ?, ?, ?, ?)""", stage)
    conn.commit()
    conn.close()


def select_all_categories():
    c = conn.cursor()
    c.execute("""SELECT * FROM [category]""")

    categories = [category[0] for category in c.fetchall()]

    return categories
