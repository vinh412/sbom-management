import React, { useState } from "react";
import {
  Form,
  Input,
  Button,
  Checkbox,
  theme,
  Typography,
  message,
} from "antd";
import { login } from "../../api/login";
import { useNavigate } from "react-router-dom";

function Login() {
  const {
    token: { colorBgContainer, colorBgLayout, borderRadiusLG, paddingLG },
  } = theme.useToken();
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const onFinish = async (values) => {
    setLoading(true);
    try {
      const data = await login(values);
      localStorage.setItem("jwt", data.token);
      navigate("/projects");
    } catch (error) {
      message.error(
        "Login failed. Please check your credentials and try again."
      );
      console.log(error);
    } finally {
      setLoading(false);
    }
  };

  const onFinishFailed = (errorInfo) => {
    console.log("Failed:", errorInfo);
  };

  return (
    <div
      style={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "100vh",
        backgroundColor: colorBgLayout,
      }}
    >
      <div
        style={{
          backgroundColor: colorBgContainer,
          borderRadius: borderRadiusLG,
          padding: paddingLG,
        }}
      >
        <div
          style={{
            display: "flex",
            flexDirection: "column",
            gap: "16px",
          }}
        >
          <div
            style={{
              display: "flex",
              justifyContent: "center",
            }}
          >
            <Typography.Title level={3} style={{ margin: 0 }}>
              SBOM Management
            </Typography.Title>
          </div>
          <Typography.Title style={{ marginTop: 0 }} level={5}>
            Login to your account
          </Typography.Title>
        </div>
        <Form
          name="basic"
          initialValues={{ remember: true }}
          labelCol={{ span: 8 }}
          wrapperCol={{ span: 16 }}
          labelAlign="left"
          onFinish={onFinish}
          onFinishFailed={onFinishFailed}
        >
          <Form.Item
            label="Username"
            name="username"
            rules={[{ required: true, message: "Username can not empty!" }]}
          >
            <Input />
          </Form.Item>

          <Form.Item
            label="Password"
            name="password"
            rules={[{ required: true, message: "Password can not empty!" }]}
          >
            <Input.Password />
          </Form.Item>

          <Form.Item name="remember" valuePropName="checked">
            <Checkbox>Remember me</Checkbox>
          </Form.Item>

          <Form.Item>
            <Button
              style={{ width: "100%" }}
              type="primary"
              htmlType="submit"
              loading={loading}
            >
              Login
            </Button>
          </Form.Item>
        </Form>
      </div>
    </div>
  );
}

export default Login;