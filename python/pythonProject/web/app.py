from flask import Flask, render_template

from auth import auth_bp

# 创建Flash应用程序实例
app = Flask(__name__)

# Register the authentication blueprint
app.register_blueprint(auth_bp)

# 路由和试图
@app.route('/')
def hello_world():  # put application's code here
    return 'Hello World!'

if __name__ == '__main__':
    app.run(debug=True)