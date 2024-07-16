package hexlet.code.controller.api;

import hexlet.code.DTO.LabelsDTO.LabelCreateDTO;
import hexlet.code.DTO.LabelsDTO.LabelDTO;
import hexlet.code.DTO.LabelsDTO.LabelUpdateDTO;
import hexlet.code.service.LabelService;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping(path = "/api")
@AllArgsConstructor
public class LabelController {
    private final LabelService labelService;

    @GetMapping(path = "/labels")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<LabelDTO>> index() {
        var result = labelService.getAll();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(result.size()))
                .body(result);
    }

    @GetMapping(path = "/labels/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LabelDTO> show(@PathVariable Long id) {
        var result = labelService.findById(id);

        return ResponseEntity.ok()
                .body(result);
    }

    @PostMapping(path = "/labels")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LabelDTO> create(@Valid @RequestBody LabelCreateDTO data) {
        var result = labelService.create(data);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(result);
    }
    @PutMapping(path = "/labels/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LabelDTO update(@PathVariable Long id, @Valid @RequestBody LabelUpdateDTO data) {
        return labelService.update(id, data);
    }

    @DeleteMapping(path = "/labels/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        labelService.delete(id);
    }
}
