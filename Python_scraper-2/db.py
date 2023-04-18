import pyodbc as pyodbc

DRIVER_NAME = 'SQL SERVER'
SERVER_NAME = 'DESKTOP-8KEOEI7\\SQLEXPRESS'
DB_NAME = 'CyCalFX23'

conn = pyodbc.connect('Driver=' + DRIVER_NAME + ';'
                      'Server=' + SERVER_NAME + ';'
                      'Database=' + DB_NAME + ';'
                      'Trusted_Connection=yes;')


def select_all_categories():
    c = conn.cursor()
    c.execute("""SELECT * FROM [category]""")

    categories = [category[0] for category in c.fetchall()]

    return categories