package hexlet.code.controller.api;

import hexlet.code.DTO.LabelsDTO.LabelCreateDTO;
import hexlet.code.DTO.LabelsDTO.LabelDTO;
import hexlet.code.DTO.LabelsDTO.LabelUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.LabelMapper;
import hexlet.code.repository.LabelRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/labels")
@AllArgsConstructor
public class LabelController {
    private final LabelRepository labelRepository;
    private final LabelMapper labelMapper;

    @GetMapping(path = "")
    public ResponseEntity<List<LabelDTO>> index() {
        var labels = labelRepository.findAll();
        var result = labels.stream()
                .map(labelMapper::map)
                .toList();

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(result.size()))
                .body(result);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<LabelDTO> show(@PathVariable Long id) {
        var label = labelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Label with id " + id + " not found"));
        var result = labelMapper.map(label);

        return ResponseEntity.ok()
                .body(result);
    }

    @PostMapping(path = "")
    public ResponseEntity<LabelDTO> create(@Valid @RequestBody LabelCreateDTO data) {
        var label = labelMapper.map(data);
        labelRepository.save(label);
        var result = labelMapper.map(label);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(result);
    }
    @PutMapping(path = "/{id}")
    public ResponseEntity<LabelDTO> update(@PathVariable Long id, @Valid @RequestBody LabelUpdateDTO data) {
        var label = labelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Label with id " + id + " not found"));
        labelMapper.update(data, label);
        labelRepository.save(label);
        var result = labelMapper.map(label);
        return ResponseEntity.ok()
                .body(result);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> destroy(@PathVariable Long id) {
        labelRepository.deleteById(id);
        return ResponseEntity.noContent()
                .build();
    }
}
