const express = require("express");
const Task = require("../model/task");
const auth = require("../middleware/auth");

const routes = new express.Router();

// Creating task
routes.post("/task", auth, async (req, res) => {
  try {

   const taskData = req.body;
    if (taskData.subtasks && taskData.subtasks.length !== 0) {
      const currentTime = new Date(); // Get the current time

      for (const subtask of taskData.subtasks) {
        // Set the start time of each subtask to the current time
        subtask.startTime = currentTime;
      }
    }
    // Sort subtasks by end time
    if (taskData.subtasks && taskData.subtasks.length > 0) {
      taskData.subtasks.sort((a, b) => {
        const timeA = new Date(a.endTime).getTime();
        const timeB = new Date(b.endTime).getTime();
        return timeA - timeB;
      });
    }

    const task = new Task({ ...taskData, owner: req.user._id });

    // Check if subtasks exist and update main task's end time if needed
    if (taskData.subtasks && taskData.subtasks.length > 0) {
      const maxSubtaskEndTime = new Date(
        taskData.subtasks[taskData.subtasks.length - 1].endTime
      );
      const taskEndTime = new Date(taskData.endTime);

      if (taskEndTime < maxSubtaskEndTime) {
        task.endTime = maxSubtaskEndTime;
      }
    }
    if (task.endTime < new Date()) {
      res.status(500).send({ message: "Error creating task" });
    }
    await task.save();
    res.status(201).send({ message: "Task Created" });
  } catch (err) {
    console.error(err);
    res.status(500).send({ message: "Error creating task" });
  }
});

// ADD sub task to exist task

routes.post("/task/:id/sub-task", auth, async (req, res) => {
  try {
    const taskId = req.params.id;
    const task = await Task.findOne({ _id: taskId, owner: req.user._id });

    if (!task) {
      return res.status(404).json({ message: "Task not found" });
    }

    const newSubtask = req.body;
    newSubtask.startTime = new Date();
    const newSubtaskEndTime = new Date(newSubtask.endTime);
    const taskEndTime = new Date(task.endTime);

    if (newSubtaskEndTime > taskEndTime) {
      return res.status(400).json({
        message: "Subtask end time cannot exceed main task's end time",
      });
    }
    if (newSubtaskEndTime < Date.now()) {
      return res.status(400).json({
        message: "Subtask end time cannot be in the past",
      });
    }
    // Add new subtask to task's subtasks array
    task.subtasks = task.subtasks || [];
    task.subtasks.push(newSubtask);

    // Sort subtasks by end time
    task.subtasks.sort((a, b) => {
      const timeA = new Date(a.endTime).getTime();
      const timeB = new Date(b.endTime).getTime();
      return timeA - timeB;
    });

    // Save the updated task
    await task.save();

    res.status(201).send({ message: "SubTask Successfully Added"});
  } catch (error) {
    console.error(error);
    res.status(500).send("Error creating subtask");
  }
});

// Read Tasks
routes.get("/task", auth, async (req, res) => {
  try {
    const date = new Date();
    let tasks = [];

    await req.user.populate("tasks");
    const userTasks = req.user.tasks;

    if (req.query.status === "pending") {
      tasks = userTasks
        .filter(
          (task) => task.completed === false && new Date(task.endTime) < date
        )
        .map((task) => ({
          id: task._id,
          title: task.title,
          description: task.description,
          endTime: task.endTime,
          priority: task.priority,
          completed: task.completed,
          createdAt: task.createdAt,
          completedDate: "0",
          subtask: task.subtasks,
          nextSubtask: null,
          daysLeft: 0,
          timeRemaining: "0 hrs 0 mins",
          completedSubtasksCount: task.completedSubtasksCount,
          totalSubtasksCount: task.totalSubtasksCount,
        }));
    } else if (req.query.status === "inProgress") {
      tasks = userTasks
        .filter((task) => !task.completed && new Date(task.endTime) > date)
        .map((task) => ({
          id: task._id,
          title: task.title,
          description: task.description,
          endTime: task.endTime,
          priority: task.priority,
          completed: task.completed,
          createdAt: task.createdAt,
          completedDate: "0",
          subtask: task.subtasks,
          nextSubtask: task.nextSubtask,
          daysLeft: task.daysLeft,
          timeRemaining: task.timeRemaining,
          completedSubtasksCount: task.completedSubtasksCount,
          totalSubtasksCount: task.totalSubtasksCount,
        }));
    } else if (req.query.status === "completed") {
      tasks = userTasks
        .filter((task) => task.completed)
        .map((task) => ({
          id: task._id,
          title: task.title,
          description: task.description,
          endTime: task.endTime,
          priority: task.priority,
          completed: task.completed,
          createdAt: task.createdAt,
          completedDate: task.completedDate,
          subtask: task.subtasks,
          nextSubtask: null,
          daysLeft: 0,
          timeRemaining: "0 hrs 0 mins",
          completedSubtasksCount: task.completedSubtasksCount,
          totalSubtasksCount: task.totalSubtasksCount,
        }));
    } else {
      return res.status(400).json({ message: "Invalid status parameter" });
    }

    res.status(200).json({ tasks });
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: "Error fetching tasks" });
  }
});

// read a single task
routes.get("/task/:id", auth, async (req, res) => {
  try {
    const date = new Date();
    const id = req.params.id;
    const task = await Task.findOne({ _id: id, owner: req.user._id });
    var tasks;
    if (task.endTime < date && !task.completed) {
      tasks = {
        id: task._id,
        title: task.title,
        description: task.description,
        endTime: task.endTime,
        priority: task.priority,
        completed: task.completed,
        createdAt: task.createdAt,
        completedDate: "0",
        subtask: task.subtasks,
        nextSubtask: task.nextSubtask,
        daysLeft: 0,
        timeRemaining: "0 hrs 0 mins",
        completedSubtasksCount: task.completedSubtasksCount,
        totalSubtasksCount: task.totalSubtasksCount,
      };
    } else if (task.completed) {
      tasks = {
        id: task._id,
        title: task.title,
        description: task.description,
        endTime: task.endTime,
        priority: task.priority,
        completed: task.completed,
        createdAt: task.createdAt,
        completedDate: task.completedDate,
        subtask: task.subtasks,
        nextSubtask: null,
        daysLeft: 0,
        timeRemaining: "0 hrs 0 mins",
        completedSubtasksCount: task.completedSubtasksCount,
        totalSubtasksCount: task.totalSubtasksCount,
      };
    } else if (task.completed == false && task.endTime > date) {
      tasks = {
        id: task._id,
        title: task.title,
        description: task.description,
        endTime: task.endTime,
        priority: task.priority,
        completed: task.completed,
        createdAt: task.createdAt,
        completedDate: "0",
        subtask: task.subtasks,
        nextSubtask: task.nextSubtask,
        daysLeft: task.daysLeft,
        timeRemaining: task.timeRemaining,
        completedSubtasksCount: task.completedSubtasksCount,
        totalSubtasksCount: task.totalSubtasksCount,
      };
    } else {
      return res.status(400).json({ message: "Invalid status parameter" });
    }

    res.status(200).json(tasks );
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: "Error fetching tasks" });
  }
});

// DELETE Main Task

routes.delete("/task/:id", auth, async (req, res) => {
  try {
    const id = req.params.id;
    const task = await Task.findOne({ _id: id, owner: req.user._id });
    await task.deleteOne();
    res.status(201).send({message:"SucessFully Removed"});
  } catch (err) {
    console.error(err);
    res.status(500).send({message:"Error"});
  }
});

//Delete Sub task
routes.delete("/task/:taskId/sub-task/:subtaskId", auth, async (req, res) => {
  try {
    const taskId = req.params.taskId;
    const subtaskId = req.params.subtaskId;

    const task = await Task.findOne({ _id: taskId, owner: req.user._id });

    if (!task) {
      return res.status(404).json({ message: "Task not found" });
    }

    // Find the index of the subtask to remove
    const subtaskIndex = task.subtasks.findIndex(subtask => subtask._id.equals(subtaskId));

    if (subtaskIndex === -1) {
      return res.status(404).json({ message: "Subtask not found" });
    }

    // Remove the subtask from the subtasks array
    task.subtasks.splice(subtaskIndex, 1);
    await task.save();

    res.status(201).send({ message: "Successfully removed subtask" });
  } catch (err) {
    console.error(err);
    res.status(500).send({ message: "Error deleting subtask" });
  }
});



// Mark completed Sub Task
routes.patch(
  "/complete/task/:id/sub-task/:subTaskId",
  auth,
  async (req, res) => {
    try {
      
      const taskId = req.params.id;
      const subTaskId = req.params.subTaskId;
      const task = await Task.findOne({ _id: taskId, owner: req.user._id });
      if (!task) {
        return res.status(400).send({ message: "Task not found" });
      }
      if (task.completed) {
        return res.status(404).json({ message: "Task Already Completed" });
      }
      if (subTaskId === "0") {
        task.completed = true;
        task.completedDate = new Date();
        await task.save();
        return res.status(202).send();
      }

      const subTaskToComplete = task.subtasks.id(subTaskId);

      if (!subTaskToComplete) {
        return res.status(404).json({ message: "Subtask not found" });
      }
      if (subTaskToComplete.completed) {
        return res.status(404).json({ message: "Task Already Completed" });
      }

      subTaskToComplete.completed = true;
      subTaskToComplete.completedDate = new Date();

      // Recalculate completed subtasks count
      const completedSubtasksCount = task.subtasks.reduce((count, subtask) => {
        if (subtask.completed) {
          return count + 1;
        }
        return count;
      }, 0);

      // Update main task completion status if all subtasks are completed
      task.completed = completedSubtasksCount === task.subtasks.length;
      if(completedSubtasksCount === task.subtasks.length)
      {
        task.completedDate = new Date();
      }
      req.user.star= req.user.star + 1;
      await req.user.save();
      await task.save();
      return res.status(202).send({message:"SuccessFully Done"});
    } catch (err) {
      console.error(err);
      return res.status(500).send({message:"Error completing task/subtask"});
    }
  }
);

module.exports = routes;
