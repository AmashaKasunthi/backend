/*package com.oceanview.resort.controller;

import com.oceanview.resort.model.Room;
import com.oceanview.resort.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin
public class RoomController {
    @Autowired
    RoomService service;

    @PostMapping
    public Room add(@RequestBody Room r){ return service.addRoom(r); }
    @GetMapping public List<Room> all(){ return service.all(); }
    @DeleteMapping("/{id}") public void del(@PathVariable Long id){ service.delete(id); }
}*/
/*package com.oceanview.resort.controller;

import com.oceanview.resort.model.Room;
import com.oceanview.resort.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "http://localhost:5173")
public class RoomController {

    @Autowired
    RoomService service;

    private final String UPLOAD_DIR = "D:/oceanview_Resort/ss/backend/uploads/";

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Room addRoom(
            @RequestParam String roomType,
            @RequestParam double price,
            @RequestParam int totalRooms,
            @RequestParam MultipartFile image
    ) throws IOException {

        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) dir.mkdirs();

        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        File dest = new File(UPLOAD_DIR + fileName);
        image.transferTo(dest);

        Room r = new Room();
        r.setRoomType(roomType);
        r.setPrice(price);
        r.setTotalRooms(totalRooms);
        r.setAvailableRooms(totalRooms);
        r.setImage(fileName);

        return service.addRoom(r);
    }

    @GetMapping
    public List<Room> all() {
        return service.all();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}*/
/*package com.oceanview.resort.controller;

import com.oceanview.resort.model.Room;
import com.oceanview.resort.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "http://localhost:5173")
public class RoomController {

    @Autowired
    RoomService service;

    private final String UPLOAD_DIR = "D:/oceanview_Resort/ss/backend/uploads/";

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Room> addRoom(
            @RequestParam String roomType,
            @RequestParam double price,
            @RequestParam int totalRooms,
            @RequestParam MultipartFile image
    ) throws IOException {

        // Create directory if it doesn't exist
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate unique filename
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        // Save file
        Files.copy(image.getInputStream(), filePath);

        // Create and save room
        Room r = new Room();
        r.setRoomType(roomType);
        r.setPrice(price);
        r.setTotalRooms(totalRooms);
        r.setAvailableRooms(totalRooms);
        r.setImage(fileName);

        Room savedRoom = service.addRoom(r);
        return ResponseEntity.ok(savedRoom);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Room> updateRoom(
            @PathVariable Long id,
            @RequestParam String roomType,
            @RequestParam double price,
            @RequestParam int totalRooms,
            @RequestParam(required = false) MultipartFile image
    ) throws IOException {

        Room existingRoom = service.findById(id);
        if (existingRoom == null) {
            return ResponseEntity.notFound().build();
        }

        // Update basic fields
        existingRoom.setRoomType(roomType);
        existingRoom.setPrice(price);

        // Calculate available rooms based on the difference
        int difference = totalRooms - existingRoom.getTotalRooms();
        existingRoom.setTotalRooms(totalRooms);
        existingRoom.setAvailableRooms(existingRoom.getAvailableRooms() + difference);

        // Update image if new one is provided
        if (image != null && !image.isEmpty()) {
            // Delete old image
            Path oldImagePath = Paths.get(UPLOAD_DIR + existingRoom.getImage());
            if (Files.exists(oldImagePath)) {
                Files.delete(oldImagePath);
            }

            // Save new image
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(image.getInputStream(), filePath);

            existingRoom.setImage(fileName);
        }

        Room updatedRoom = service.updateRoom(existingRoom);
        return ResponseEntity.ok(updatedRoom);
    }

    @GetMapping
    public ResponseEntity<List<Room>> all() {
        return ResponseEntity.ok(service.all());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getById(@PathVariable Long id) {
        Room room = service.findById(id);
        if (room == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(room);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Room room = service.findById(id);
        if (room != null && room.getImage() != null) {
            // Delete image file
            try {
                Path imagePath = Paths.get(UPLOAD_DIR + room.getImage());
                if (Files.exists(imagePath)) {
                    Files.delete(imagePath);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}*/
package com.oceanview.resort.controller;

import com.oceanview.resort.model.Room;
import com.oceanview.resort.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "http://localhost:5173")
public class RoomController {

    @Autowired
    RoomService service;

    // CHANGE THIS TO YOUR ABSOLUTE PATH
    private final String UPLOAD_DIR = "D:/oceanview_Resort/ss/backend/uploads/";

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Room> addRoom(
            @RequestParam String roomType,
            @RequestParam double price,
            @RequestParam int totalRooms,
            @RequestParam String description,
            @RequestParam MultipartFile image
    ) throws IOException {

        // Validate file type - ONLY PNG, JPG, JPEG
        String contentType = image.getContentType();
        if (contentType == null ||
                (!contentType.equals("image/png") &&
                        !contentType.equals("image/jpeg") &&
                        !contentType.equals("image/jpg"))) {
            throw new IOException("Only PNG, JPG, and JPEG files are allowed");
        }

        // Create directory if it doesn't exist
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate unique filename
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        // Save file
        Files.copy(image.getInputStream(), filePath);

        // Create and save room
        Room r = new Room();
        r.setRoomType(roomType);
        r.setPrice(price);
        r.setTotalRooms(totalRooms);
        r.setAvailableRooms(totalRooms);
        r.setDescription(description);
        r.setImage(fileName);

        Room savedRoom = service.addRoom(r);
        return ResponseEntity.ok(savedRoom);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Room> updateRoom(
            @PathVariable Long id,
            @RequestParam String roomType,
            @RequestParam double price,
            @RequestParam int totalRooms,
            @RequestParam String description,
            @RequestParam(required = false) MultipartFile image
    ) throws IOException {

        Room existingRoom = service.findById(id);
        if (existingRoom == null) {
            return ResponseEntity.notFound().build();
        }

        // Validate file type if image is provided - ONLY PNG, JPG, JPEG
        if (image != null && !image.isEmpty()) {
            String contentType = image.getContentType();
            if (contentType == null ||
                    (!contentType.equals("image/png") &&
                            !contentType.equals("image/jpeg") &&
                            !contentType.equals("image/jpg"))) {
                throw new IOException("Only PNG, JPG, and JPEG files are allowed");
            }
        }

        // Update basic fields
        existingRoom.setRoomType(roomType);
        existingRoom.setPrice(price);
        existingRoom.setDescription(description);

        // Calculate available rooms based on the difference
        int difference = totalRooms - existingRoom.getTotalRooms();
        existingRoom.setTotalRooms(totalRooms);
        existingRoom.setAvailableRooms(existingRoom.getAvailableRooms() + difference);

        // Update image if new one is provided
        if (image != null && !image.isEmpty()) {
            // Delete old image
            Path oldImagePath = Paths.get(UPLOAD_DIR + existingRoom.getImage());
            if (Files.exists(oldImagePath)) {
                Files.delete(oldImagePath);
            }

            // Save new image
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(image.getInputStream(), filePath);

            existingRoom.setImage(fileName);
        }

        Room updatedRoom = service.updateRoom(existingRoom);
        return ResponseEntity.ok(updatedRoom);
    }

    @GetMapping
    public ResponseEntity<List<Room>> all() {
        return ResponseEntity.ok(service.all());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getById(@PathVariable Long id) {
        Room room = service.findById(id);
        if (room == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(room);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Room room = service.findById(id);
        if (room != null && room.getImage() != null) {
            // Delete image file
            try {
                Path imagePath = Paths.get(UPLOAD_DIR + room.getImage());
                if (Files.exists(imagePath)) {
                    Files.delete(imagePath);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}