import { Button, theme, Typography } from 'antd';
import React from 'react'
import { Link } from 'react-router-dom';

function BuildList({ builds }) {
  const {token : {borderRadius, padding, paddingSM, colorBgContainer}} = theme.useToken();
  return (
    <div style={{
      borderRadius: borderRadius,
      paddingTop: padding,
      paddingBottom: padding,
      paddingLeft: paddingSM,
      paddingRight: paddingSM,
      background: colorBgContainer,
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center'
    }}>
      <Typography.Title level={4} style={{margin: 0}}>Builds</Typography.Title>
        {builds.map(({name, createdAt}) => (
          <BuildItem key={name} buildNumber={name} time={createdAt} />
        ))}
      <Link>More</Link>
    </div>
  )
}

function BuildItem({buildNumber, time}) {
  const {token : {borderRadiusSM, paddingXS}} = theme.useToken();
  return (
    <div style={{
      padding: paddingXS,
      borderRadius: borderRadiusSM,
      display: 'flex',
      flexDirection: 'column',
    }}>
      <Link type='link'>{buildNumber}</Link>
      <Typography.Text>{time}</Typography.Text>
    </div>
  )
}

export default BuildList