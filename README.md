# Opereating System - Process Management
## 1 Objective

This project simulates the behavior of the multiprogramming operating system and use CPU scheduler, and CPU Execution. At the end of the simulation, it's expected to output some statistics regarding the behavior of the system.
## 2 Specification

You can find the hardware specification, the multiprogramming OS features and the jobs requirements and more in [this link](https://github.com/Omar-Al-Khathlan/OS-Project/blob/master/Specification/Specification.pdf).
## 3 Output Of The Project

Output from the simulation: A text file containing statistics about all processes and their final status TERMINATED or KILLED. Statistics about a process should contain:

- Process ID
- When it was loaded into the ready queue
- Number of times it was in the CPU
- Total time spent in the CPU
- Number of times it performed an IO
- Total time spent in performing IO
- Number of times it was waiting for memory
- Number of times its preempted (stopped execution because another process replaced it)
- Time it terminated or was killed
- Its' final state: Killed or Terminated

- And should also output the CPU Utilization of the system .
