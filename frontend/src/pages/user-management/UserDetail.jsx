import { Button, Col, Form, Input, Row } from "antd";
import React from "react";
import userApi from "../../api/user";
import { useLoaderData, useNavigate } from "react-router-dom";

export async function loader({ params }) {
  const user = await userApi.getUserByUsername(params.username);
  return { user };
}

function UserDetail() {
  const { user } = useLoaderData();
  const [isEdit, setIsEdit] = React.useState(false);
  const navigate = useNavigate();
  return (
    <div>
      <Form layout="vertical" disabled={!isEdit}>
        <Row gutter={12}>
          <Col span={12}>
            <Form.Item label="Username">
              <Input value={user.username} />
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item label="Fullname">
              <Input value={user.fullname} />
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item label="Email">
              <Input value={user.email} />
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item label="Phone Number">
              <Input value={user.phoneNumber} />
            </Form.Item>
          </Col>
        </Row>
        <Form.Item label="Description">
          <Input value={user.description} />
        </Form.Item>
        <Form.Item label="Created At">
          <Input value={user.createdAt} disabled/>
        </Form.Item>
      </Form>
      <div
        style={{
          display: "flex",
          justifyContent: "center",
          gap: "12px",
        }}
      >
        <Button onClick={() => {
          if(isEdit) {
            setIsEdit(false);
          }else{
            navigate("/admin/users");
          }
        }}>Cancel</Button>
        {isEdit ? (
          <Button type="primary" onClick={() => setIsEdit(false)}>
            Save
          </Button>
        ) : (
          <Button type="primary" onClick={() => setIsEdit(true)}>
            Edit
          </Button>
        )}
      </div>
    </div>
  );
}

export default UserDetail;
