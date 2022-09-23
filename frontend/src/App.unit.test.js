import { render, screen } from '@testing-library/react'

import App from './App'

test('renders App component', () => {
  render(<App />)

  // add task accordian, label, input, button
  expect(screen.getByText('Add Task')).toBeInTheDocument()
  expect(screen.getAllByLabelText('Description *')[0]).toHaveAttribute('id', 'addName')
  expect(screen.getAllByLabelText('Time')[0]).toHaveAttribute('placeholder', 'mm/dd/yyyy hh:mm (a|p)m')
  expect(screen.getByText('Add')).toBeInTheDocument()
  // update task accordian, label, input, button
  expect(screen.getByText('Update Task')).toBeInTheDocument()
  expect(screen.getByLabelText('ID *')).toHaveAttribute('id', 'updateId')
  expect(screen.getAllByLabelText('Time')[1]).toHaveAttribute('placeholder', 'mm/dd/yyyy hh:mm (a|p)m')
  expect(screen.getAllByLabelText('Description *')[1]).toHaveAttribute('id', 'updateName')
  expect(screen.getByText('Update')).toBeInTheDocument()
  // task list
  expect(screen.getByTestId('taskList')).toBeInTheDocument()
  // delete button
  expect(screen.getByText('Delete')).toBeInTheDocument()
  //screen.debug(null, 40000)
})

