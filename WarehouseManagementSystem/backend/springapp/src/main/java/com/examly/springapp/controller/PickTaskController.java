
package com.examly.springapp.controller;

import com.examly.springapp.dto.PickTaskDTO;
import com.examly.springapp.entity.PickTask;
import com.examly.springapp.service.PickTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pick-tasks")
public class PickTaskController {

    @Autowired
    private PickTaskService pickTaskService;

    // POST /api/pick-tasks
    @PostMapping
    public ResponseEntity<PickTask> createPickTask(@RequestBody PickTaskDTO dto) {
        PickTask created = pickTaskService.createPickTask(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // GET /api/pick-tasks
    @GetMapping
    public ResponseEntity<List<PickTask>> getAllPickTasks() {
        return ResponseEntity.ok(pickTaskService.getAllPickTasks());
    }

    // GET /api/pick-tasks/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PickTask> getPickTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(pickTaskService.getPickTaskById(id));
    }

    // GET /api/pick-tasks/order/{orderId}
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<PickTask>> getPickTasksByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(pickTaskService.getPickTasksByOrder(orderId));
    }

    // GET /api/pick-tasks/status/{status}
    @GetMapping("/status/{status}")
    public ResponseEntity<List<PickTask>> getPickTasksByStatus(@PathVariable String status) {
        return ResponseEntity.ok(pickTaskService.getPickTasksByStatus(status));
    }

    // PUT /api/pick-tasks/{id}/status?status=IN_PROGRESS
    @PutMapping("/{id}/status")
    public ResponseEntity<PickTask> updatePickTaskStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return ResponseEntity.ok(pickTaskService.updatePickTaskStatus(id, status));
    }

    // DELETE /api/pick-tasks/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePickTask(@PathVariable Long id) {
        pickTaskService.deletePickTask(id);
        return ResponseEntity.ok("Pick task deleted successfully");
    }
}

