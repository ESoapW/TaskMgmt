import axios from 'axios'
import API from './baseUrl'
import userEvent from '@testing-library/user-event'
import { render, screen, act} from '@testing-library/react'

import App from './App'
import { scryRenderedComponentsWithType } from 'react-dom/test-utils'
import { Update } from '@mui/icons-material'

jest.mock(`./baseUrl`)


const alertMock = jest.spyOn(window,'alert').mockImplementation() 

delete window.location
window.location = { reload: jest.fn() }
const reloadMock = jest.spyOn(window.location, 'reload').mockImplementation()

window.confirm = jest.fn().mockImplementation(() => true)

const tasks = [
    { id: '1', name: 'Onboarding - first day', date: '2022-05-17T09:00+0800' },
    { id: '2', name: 'Offboarding - last day', date: '2022-09-16T18:00+0800' }
]

describe('App', () => {
    test('fetches tasks from an API and displays them', async () => {
      API.get.mockImplementationOnce(() => 
        Promise.resolve({ data: tasks })
      )

      await act(async() => {
        render(<App />) 
      })

      const items = await screen.findAllByRole('listitem')
      expect(items).toHaveLength(2)
    })

    test('creates a task and post to API', async () => {
        const create_task = { id: '3', name: 'Create test', date: '2022-05-17T09:00+0800' }

        API.get.mockImplementationOnce(() => 
          Promise.resolve({ data: tasks })
        )

        API.post.mockImplementationOnce(() => 
          Promise.resolve({ data: create_task })
        )
        
        await act(async() => {
            render(<App />) 
        })

        await userEvent.type(screen.getAllByLabelText('Description *')[0], 'Create test')
        
        await userEvent.click(screen.getByText('Add'))

        expect(reloadMock).toHaveBeenCalledTimes(1)
    })
    
    test('fails to create a task and post to API - blank name', async () => {
        API.get.mockImplementationOnce(() => 
          Promise.resolve({ data: tasks })
        )
        
        await act(async() => {
            render(<App />) 
        })

        await userEvent.click(screen.getByText('Add')) 
        expect(alertMock).toHaveBeenCalledTimes(1)
    })

    test('updates a task and put to API', async () => {
        const update_task = { id: '1', name: 'Update test', date: '2022-05-17T09:00+0800' }

        API.get.mockImplementationOnce(() => 
          Promise.resolve({ data: tasks })
        )

        API.put.mockImplementationOnce(() => 
          Promise.resolve({ data: update_task })
        )
        
        await act(async() => {
            render(<App />) 
        })

        await userEvent.type(screen.getByLabelText('ID *'), '1')
        await userEvent.type(screen.getAllByLabelText('Description *')[1], 'Update test')
        
        await userEvent.click(screen.getByText('Update'))

        expect(reloadMock).toHaveBeenCalledTimes(1)
    })

    test('fails to update a task and put to API - blank id and name', async () => {
        API.get.mockImplementationOnce(() => 
          Promise.resolve({ data: tasks })
        )
        
        await act(async() => {
            render(<App />) 
        })

        await userEvent.click(screen.getByText('Update')) 
        expect(alertMock).toHaveBeenCalledTimes(1)
    })

    test('deletes a task', async () => {
        const delete_task = [{ id: '1', name: 'Delete test', date: '2022-05-17T09:00+0800' }]

        API.get.mockImplementationOnce(() => 
          Promise.resolve({ data: delete_task })
        )
  
        await act(async() => {
          render(<App />) 
        })

        await userEvent.click(screen.getByRole('checkbox'))
        await userEvent.click(screen.getByText('Delete'))
        expect(window.confirm).toHaveBeenCalledTimes(1)
        //screen.debug(null, 40000)
      })
  })
