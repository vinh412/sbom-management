import { Select } from 'antd'
import React from 'react'

function CompareBuild() {
  const options = [
    {
      label: '#1',
      value: 'build1'
    },
    {
      label: '#2',
      value: 'build2'
    },
    {
      label: '#3',
      value: 'build3'
    }
  ]
  return (
    <div style={{
      display: 'flex',
      justifyContent: 'center',
      gap: '8px'
    }}>
      <Select style={{
        minWidth: '200px',
      }} options={options}/>
      <Select style={{
        minWidth: '200px',
      }} options={options}/>
    </div>
  )
}

export default CompareBuild