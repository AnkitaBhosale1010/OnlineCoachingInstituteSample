package com.coaching.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.coaching.dao.BatchDao;
import com.coaching.dao.StudentDao;
import com.coaching.dto.BatchDto;
import com.coaching.dto.BatchResponseDto;
import com.coaching.dto.StudentResponseDto;
import com.coaching.entity.Batch;
import com.coaching.entity.Student;

@Service
public class BatchServiceImpl implements BatchService{

	private final BatchDao batchDao;
    private final StudentDao studentDao;

    public BatchServiceImpl(BatchDao batchDao,StudentDao studentDao) {

        this.batchDao = batchDao;
        this.studentDao = studentDao;
    }

    @Override
    public BatchResponseDto createBatch(BatchDto dto) {

        Batch batch = new Batch();

        batch.setBatchName(dto.getBatchName());
        batch.setTrainerName(dto.getTrainerName());
        batch.setStartDate(dto.getStartDate());
        batch.setEndDate(dto.getEndDate());
        batch.setStatus(dto.getStatus());

        return convertToDto(batchDao.save(batch));
    }

    @Override
    public List<BatchResponseDto> getAllBatches() {

        List<BatchResponseDto> list = new ArrayList<>();

        batchDao.findAll().forEach(batch -> {

            list.add(convertToDto(batch));

        });

        return list;
    }

    @Override
    public BatchResponseDto getBatchById(Long id) {

        Batch batch = batchDao.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Batch Not Found"));

        return convertToDto(batch);
    }

    @Override
    public BatchResponseDto updateBatch(Long id,
                                        BatchDto dto) {

        Batch batch = batchDao.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Batch Not Found"));

        batch.setBatchName(dto.getBatchName());
        batch.setTrainerName(dto.getTrainerName());
        batch.setStartDate(dto.getStartDate());
        batch.setEndDate(dto.getEndDate());
        batch.setStatus(dto.getStatus());

        return convertToDto(batchDao.save(batch));
    }

    @Override
    public void deleteBatch(Long id) {

        batchDao.deleteById(id);

    }

    @Override
    public Student assignStudent(Long studentId,
                                 Long batchId) {

        Student student = studentDao.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException("Student Not Found"));

        Batch batch = batchDao.findById(batchId)
                .orElseThrow(() ->
                        new RuntimeException("Batch Not Found"));

        student.setBatch(batch);

        return studentDao.save(student);

    }

    @Override
    public List<Student> getStudentsByBatch(Long batchId) {

        return studentDao.findByBatchId(batchId);

    }

    // ===============================
    // DTO Converter
    // ===============================

    private BatchResponseDto convertToDto(Batch batch) {

        BatchResponseDto dto = new BatchResponseDto();

        dto.setId(batch.getId());
        dto.setBatchName(batch.getBatchName());
        dto.setTrainerName(batch.getTrainerName());
        dto.setStartDate(batch.getStartDate());
        dto.setEndDate(batch.getEndDate());
        dto.setStatus(batch.getStatus());

        List<StudentResponseDto> students = new ArrayList<>();

        if (batch.getStudents() != null) {

            for (Student s : batch.getStudents()) {

                StudentResponseDto sdto = new StudentResponseDto();

                sdto.setStudentId(s.getStudentId());

                if (s.getUser() != null) {

                    sdto.setName(s.getUser().getName());
                    sdto.setEmail(s.getUser().getEmail());

                }

                sdto.setPhone(s.getPhone());

                if (s.getBatch() != null)
                    sdto.setBatchName(s.getBatch().getBatchName());

                students.add(sdto);

            }

        }

        dto.setStudents(students);

        return dto;
    }

}
