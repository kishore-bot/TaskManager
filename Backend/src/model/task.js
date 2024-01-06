const mongoose = require("mongoose");

const taskSchema = new mongoose.Schema({
  title: { type: String, required: true },
  description: { type: String },
  createdAt: { type: Date, default: Date.now },
  endTime: { type: Date, required: true },
  subtasks: [
    {
      name: { type: String, required: true },
      description: { type: String },
      startTime: { type: Date },
      endTime: { type: Date, required: true },
      completed: { type: Boolean, default: false },
      completedDate: { type: Date },
    },
  ],
  priority: { type: String, enum: ["high", "medium", "low"] },
  completed: { type: Boolean, default: false },
  completedDate: { type: Date },
  owner: {
    type: mongoose.Schema.Types.ObjectId,
    required: true,
    ref: "User",
  },
});

taskSchema.virtual("daysLeft").get(function () {
  const now = new Date();
  const currentSubtask = this.subtasks.find(
    (subtask) => subtask.completed == false
  );
  if (currentSubtask) {
    const diffInMilliseconds = Math.max(0, currentSubtask.endTime - now);
    const diffInDays = Math.floor(diffInMilliseconds / (1000 * 3600 * 24));
    return diffInDays;
  } else {
    const diffInMilliseconds = Math.max(0, this.endTime - now);
    const diffInDays = Math.floor(diffInMilliseconds / (1000 * 3600 * 24));
    return diffInDays;
  }
});

taskSchema.virtual("nextSubtask").get(function () {
  const incompleteSubtask = this.subtasks.find((subtask) => !subtask.completed);

  if (incompleteSubtask) {
    return incompleteSubtask._id;
  } else {
    return null; // Return null if no incomplete subtasks are found
  }
});

taskSchema.virtual("completedSubtasksCount").get(function () {
  if (this.subtasks.length == 0) {
    if (this.completed == false) return 0;
    else return 1;
  }else return this.subtasks.filter((subtask) => subtask.completed).length;
});

taskSchema.virtual("totalSubtasksCount").get(function () {
  if (this.subtasks.length == 0) {
    return 1;
  } else return this.subtasks.length;
});

taskSchema.virtual("timeRemaining").get(function () {
  const now = new Date();
  const currentSubtask = this.subtasks.find(
    (subtask) => subtask.completed == false
  );
  if (currentSubtask) {
    const diff = Math.max(0, currentSubtask.endTime - now);
    const hoursRemaining = Math.floor(diff / (1000 * 60 * 60));
    const minutesRemaining = Math.floor(
      (diff % (1000 * 60 * 60)) / (1000 * 60)
    );

    return `${hoursRemaining} hrs ${minutesRemaining} mins`;
  } else {
    const diff = Math.max(0, this.endTime - now);
    const hoursRemaining = Math.floor(diff / (1000 * 60 * 60));
    const minutesRemaining = Math.floor(
      (diff % (1000 * 60 * 60)) / (1000 * 60)
    );
    return `${hoursRemaining} hrs ${minutesRemaining} mins`;
  }
});

const Task = mongoose.model("Task", taskSchema);

module.exports = Task;
