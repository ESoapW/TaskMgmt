/* eslint eqeqeq: 0 */
import './App.css'
import React, {useEffect, useState} from 'react'
import CalendarTodayOutlinedIcon from '@mui/icons-material/CalendarTodayOutlined'
import Button from '@mui/material/Button'
import CssBaseline from '@mui/material/CssBaseline'
import TextField from '@mui/material/TextField'
import Accordion from '@mui/material/Accordion'
import AccordionSummary from '@mui/material/AccordionSummary'
import AccordionDetails from '@mui/material/AccordionDetails'
import ExpandMoreIcon from '@mui/icons-material/ExpandMore'
import Box from '@mui/material/Box'
import Link from '@mui/material/Link'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import Container from '@mui/material/Container'
import { createTheme, ThemeProvider } from '@mui/material/styles'
import List from '@mui/material/List'
import ListItem from '@mui/material/ListItem'
import ListItemButton from '@mui/material/ListItemButton'
import ListItemText from '@mui/material/ListItemText'
import Checkbox from '@mui/material/Checkbox'
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker'

import API from './baseUrl'

function Copyright(props) {
  return (
    <Typography variant="body2" color="text.secondary" align="center" {...props}>
      {'Copyright © '}     
      <Link color="inherit" href="https://esoapw.github.io/yixin-wei/Home.html" target="_blank" rel="noopener">
        Wei Yixin
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  )
}

// Transform dateFormat to be IO friendly: yyyy-MM-dd'T'HH:mmZ
function getDateIO(date) {
  let dateIO = String(date.getFullYear()) + '-' +
               String(("0"+(date.getMonth()+1)).slice(-2)) + '-' +
               String(("0"+date.getDate()).slice(-2)) + 'T' +
               String(("0"+date.getHours()).slice(-2)) + ':' +
               String(("0"+date.getMinutes()).slice(-2)) + '+0800'
  return dateIO
}

async function createTask(name, date) {
  let data = {"name": name, "date": getDateIO(date)}
  API.post(`tasks`, data)
    .then(res => {
      console.log(res.data)
    })
    .catch(err => console.log(err))
}

async function updateTask(id, name, date) {
  let data = {"name": name, "date": getDateIO(date)}
  API.put(`tasks/${id}`, data)
    .then(res => {
      console.log(res.data)
    })
    .catch(err => console.log(err))
}

async function deleteTask(id) {
  API.delete(`tasks/${id}`)
    .then(res => {
      console.log(res.data)
    })
    .catch(err => console.log(err))
}


const theme = createTheme()

export default function Home() {
  const [checked, setChecked] = useState([])
  const [allTaskData, setAllTaskData] = useState([])
  const [addDate, setAddDate] = useState(new Date())
  const [updateDate, setUpdateDate] = useState(new Date())
  const [inputAddName, setInputAddName] = useState("")
  const [inputUpdateName, setInputUpdateName] = useState("")
  const [inputUpdateId, setInputUpdateId] = useState("")

  useEffect(() => {
    API.get(`tasks`)
    .then(res => {
      const tasks = res.data
      setAllTaskData(tasks)
    })
    .catch(err => console.log(err))
  }, [])

  const isIdValid = (id) => {
    for(var key in allTaskData) {
      if(allTaskData[key].id == id){ return true }
    }
    return false
  }

  const handleAddTask = (event) => {
    event.preventDefault()
    if(!inputAddName){
      alert('Description cannot be empty!')
      return
    } else {
      createTask(inputAddName, addDate)
      alert('Successfully added task!')
      window.location.reload()
    }
  }

  const handleUpdateTask = (event) => {
    event.preventDefault()

    if(!inputUpdateId || !inputUpdateName){
      alert('ID or description cannot be empty!')
      return
    } else if(isIdValid(inputUpdateId)) {
      updateTask(inputUpdateId, inputUpdateName, updateDate)
      alert(`Successfully updated task ${inputUpdateId}!`)
      window.location.reload()
    } else {
      alert('Invalid task ID!')
      setInputUpdateId("")
    }
  }

  const handleToggle = (value) => () => {
    const currentIndex = checked.indexOf(value)
    const newChecked = [...checked]

    if (currentIndex === -1) {
      newChecked.push(value)
    } else {
      newChecked.splice(currentIndex, 1)
    }

    setChecked(newChecked)
  }

  const handleDeleteTask = (event) => {
    event.preventDefault()
    let selected = checked
    if (window.confirm(`Do you want to delete task ${selected} ?`)){
      checked.forEach(deleteTask)
      alert(`Successfully deleted task ${selected} !`)
      window.location.reload()
    } else {
      return
    }
  }

  return (
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="lg">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 4,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}
        >
          <Accordion sx={{width: '59.5%'}}>
            <AccordionSummary
              expandIcon={<ExpandMoreIcon />}
              aria-controls="panel1a-content"
              id="panel1a-header"
            >
              <Typography>Add Task</Typography>
            </AccordionSummary>
            <AccordionDetails>
              <Box component="form" onSubmit={handleAddTask} noValidate sx={{ mt: 0 }}>
                <Grid item xs={12}>
                  <TextField
                    margin="normal"
                    required
                    fullWidth
                    id="addName"
                    label="Description"
                    name="addName"
                    autoComplete="name"
                    autoFocus
                    onChange={(event) => {setInputAddName(event.target.value)}}
                  />
                </Grid>
                <Grid item xs={12} sx={{mt: 1}}>
                  <LocalizationProvider dateAdapter={AdapterDateFns} >
                      <DateTimePicker
                          InputProps={{
                            style: {
                                width: '282.7%'
                            }
                          }}
                          renderInput={(props) => <TextField {...props} />}
                          label="Time"
                          name="addTime"
                          id="addTime"
                          value={addDate}
                          onChange={(newDate) => {
                              setAddDate(newDate)
                          }}
                      />
                  </LocalizationProvider>
                </Grid>
                  <Button
                    type="submit"
                    fullWidth
                    variant="contained"
                    sx={{ mt: 3, mb: 2 }}
                  >
                    Add
                  </Button>
              </Box>
            </AccordionDetails>
          </Accordion>

          <Accordion>
            <AccordionSummary
              expandIcon={<ExpandMoreIcon />}
              aria-controls="panel2a-content"
              id="panel2a-header"
            >
              <Typography>Update Task</Typography>
            </AccordionSummary>
            <AccordionDetails>
              <Box component="form" onSubmit={handleUpdateTask} noValidate sx={{ mt: 0 }}>
              <Grid container spacing={2}>
                <Grid item xs={12} sm={6}>
                  <TextField
                    autoComplete="id"
                    name="updateId"
                    required
                    fullWidth
                    id="updateId"
                    label="ID"
                    autoFocus
                    onChange={(event) => {setInputUpdateId(event.target.value)}}
                    value={inputUpdateId}
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <LocalizationProvider dateAdapter={AdapterDateFns} >
                      <DateTimePicker
                          InputProps={{
                            style: {
                                width: '138%'
                            }
                          }}
                          renderInput={(props) => <TextField {...props} />}
                          label="Time"
                          name="updateTime"
                          id="updateTime"
                          value={updateDate}
                          onChange={(newDate) => {
                              setUpdateDate(newDate)
                          }}
                      />
                  </LocalizationProvider>
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    required
                    fullWidth
                    id="updateName"
                    label="Description"
                    name="updateName"
                    autoComplete="name"
                    onChange={(event) => {setInputUpdateName(event.target.value)}}
                  />
                </Grid>
              </Grid>
                <Button
                  type="submit"
                  fullWidth
                  variant="contained"
                  sx={{ mt: 3, mb: 2 }}
                >
                  Update
                </Button>
              </Box>
            </AccordionDetails>
          </Accordion>
          
          <List dense sx={{ width: '50%', mt: 6, bgcolor: '#ececec' }}>
            {allTaskData.map((task) => {
              const labelId = `checkbox-list-secondary-label-${task.id}`
              return (
                <ListItem
                  key={task.id}
                  secondaryAction={
                    <Checkbox
                      edge="end"
                      onChange={handleToggle(task.id)}
                      checked={checked.indexOf(task.id) !== -1}
                      inputProps={{ 'aria-labelledby': labelId }}
                    />
                  }
                  disablePadding
                >
                  <ListItemButton onClick={handleToggle(task.id)}>
                    <ListItemText primary={`${task.name}`
                                   } 
                                  primaryTypographyProps={{ style: { whiteSpace: "normal" } }} 
                                  secondary={<span><CalendarTodayOutlinedIcon sx={{ color: '#717171', width: '20px', pt: 1.5, mt: -0.8, ml: -0.6}}/> 
                                                  {task.date.slice(0,16).replace("T", ' ')}<br/>
                                                  id: {task.id}</span>}/>
                  </ListItemButton>
                </ListItem>
              )
            })}
          </List>

          <Button
            disabled={checked.length==0}
            variant="contained"
            sx={{ bgcolor: "#f00e0e", mt: 3, mb: 2 }}
            onClick={handleDeleteTask}
          >
            Delete
          </Button>
          
        </Box>
        <Copyright sx={{ mt: 8, mb: 4 }} />
      </Container>
    </ThemeProvider>
  )
}