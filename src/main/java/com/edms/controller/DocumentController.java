package com.edms.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.edms.dto.DocumentDTO;
import com.edms.service.DocumentService;
@RestController
@RequestMapping("/document")
public class DocumentController {
   @Autowired
   private DocumentService documentService;
   @PostMapping("/upload")
   public ResponseEntity<String> uploadNewDocument(@RequestParam("file") MultipartFile file,
                                                     @RequestParam("title") String title,
                                                     @RequestParam("author") String author) {
	   return ResponseEntity.ok(documentService.uploadDocument(file, title, author));
   }
   @PostMapping("/upload/{documentId}")
   public ResponseEntity<String> uploadNewDocumentVersion(@RequestParam("file") MultipartFile file,
                                                     @RequestParam("author") String author,
                                                     @PathVariable("documentId") String documentId) {
	   return ResponseEntity.ok(documentService.uploadNewVersion(documentId, file, author));
   }
   @GetMapping
   public ResponseEntity<?> getAllDocuments() {
	   return ResponseEntity.ok(documentService.getAllDocuments());
   }
   
  
   @GetMapping("/{documentId}")
   public ResponseEntity<DocumentDTO> getDocument(@PathVariable("documentId") String documentId) {
	   return ResponseEntity.ok(documentService.getDocument(documentId));
   }
   @GetMapping("/{documentId}/{documentVersion}")
   public ResponseEntity<DocumentDTO> getDocumentVersion(@PathVariable("documentVersion") int version,@PathVariable("documentId") String documentId) {
	   return ResponseEntity.ok(documentService.getDocumentVersion(documentId, version));
   }
   
   @PutMapping("/{documentId}")
   public ResponseEntity<String> uploadNewDocument(@PathVariable("documentId") String documentId,
                                                     @RequestParam("title") String title,
                                                     @RequestParam("author") String author) {
	   return ResponseEntity.ok(documentService.updateDocument(documentId, title, author));
   }
   
   @DeleteMapping("/{documentId}")
   public ResponseEntity<String> deleteDocument(@PathVariable("documentId") String documentId) {
	   return ResponseEntity.ok(documentService.deleteDocument(documentId));
   }
   @DeleteMapping("/{documentId}/{documentVersion}")
   public ResponseEntity<String> deleteDocumentVersion(@PathVariable("documentVersion") int version,@PathVariable("documentId") String documentId) {
	   return ResponseEntity.ok(documentService.deleteDocumentVersion(documentId, version));
   }
}