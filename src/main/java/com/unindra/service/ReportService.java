package com.unindra.service; // Sesuaikan package lu

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unindra.model.request.ClassroomReport;
import com.unindra.model.request.StudentReport; // Sesuaikan import model lu
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ObjectMapper objectMapper;

    // PATH KONFIGURASI (Relatif dari Root Project)
    private static final String ASSETS_PATH = "assets/typst";
    private static final String DATA_SUBDIR = "data";
    private static final String OUTPUT_SUBDIR = "output"; // Folder baru buat hasil PDF
    private static final String REPORT_FILE = "report/";

    public byte[] generateStudentPdf(List<StudentReport> students) {
        // Generate ID Unik
        String uniqueId = UUID.randomUUID().toString();
        
        // 1. SETUP PATH ABSOLUT (Biar aman di Linux)
        String projectRoot = System.getProperty("user.dir");
        Path typstRoot = Paths.get(projectRoot, ASSETS_PATH);
        Path dataDir = typstRoot.resolve(DATA_SUBDIR);
        Path outputDir = typstRoot.resolve(OUTPUT_SUBDIR);

        // Define File Paths
        Path jsonFile = dataDir.resolve("data-" + uniqueId + ".json");
        Path pdfFile = outputDir.resolve("output-" + uniqueId + ".pdf");

        try {
            // 2. AUTO-CREATE FOLDER (Penting! Biar gak error kalau folder belum ada)
            if (!Files.exists(dataDir)) Files.createDirectories(dataDir);
            if (!Files.exists(outputDir)) Files.createDirectories(outputDir);

            // 3. TULIS JSON
            objectMapper.writeValue(jsonFile.toFile(), students);

            // 4. SIAPKAN COMMAND TYPST
            // Logic: typst compile [INPUT] [OUTPUT] --root [ROOT] --input key=value
            ProcessBuilder pb = new ProcessBuilder(
                "typst", 
                "compile", 
                typstRoot.resolve(REPORT_FILE).toString() + "/studentReport.typ", // Input Full Path
                pdfFile.toString(),                        // Output Full Path
                "--root", typstRoot.toString(),            // Root Project Typst
                // Pass lokasi JSON relatif terhadap --root
                "--input", "dataFile=" + DATA_SUBDIR + "/" + jsonFile.getFileName().toString() 
            );

            pb.redirectErrorStream(true);
            Process process = pb.start();

            // Tunggu maksimal 30 detik
            boolean finished = process.waitFor(30, TimeUnit.SECONDS);
            if (!finished) {
                process.destroy();
                throw new RuntimeException("Typst process timed out");
            }

            if (process.exitValue() != 0) {
                // Baca error log dari Typst CLI
                try (java.io.InputStream is = process.getInputStream()) {
                    String errorLog = new String(is.readAllBytes());
                    throw new RuntimeException("Typst Compile Error:\n" + errorLog);
                }
            }

            // 5. BACA HASIL PDF
            return Files.readAllBytes(pdfFile);

        } catch (Exception e) {
            e.printStackTrace(); // Cek log server kalo error
            throw new RuntimeException("Gagal generate PDF: " + e.getMessage(), e);
        } finally {
            // 6. CLEANUP (Hapus file sementara)
            try {
                // Thread.sleep(1);
                Files.deleteIfExists(jsonFile);
                // Files.deleteIfExists(pdfFile);
            } catch (IOException ignored) {
                // Ignore error saat delete
            }
        }
    }

    public byte[] generateClassroomPdf(List<ClassroomReport> students) {
        // Generate ID Unik
        String uniqueId = UUID.randomUUID().toString();
        
        // 1. SETUP PATH ABSOLUT (Biar aman di Linux)
        String projectRoot = System.getProperty("user.dir");
        Path typstRoot = Paths.get(projectRoot, ASSETS_PATH);
        Path dataDir = typstRoot.resolve(DATA_SUBDIR);
        Path outputDir = typstRoot.resolve(OUTPUT_SUBDIR);

        // Define File Paths
        Path jsonFile = dataDir.resolve("data-" + uniqueId + ".json");
        Path pdfFile = outputDir.resolve("output-" + uniqueId + ".pdf");

        try {
            // 2. AUTO-CREATE FOLDER (Penting! Biar gak error kalau folder belum ada)
            if (!Files.exists(dataDir)) Files.createDirectories(dataDir);
            if (!Files.exists(outputDir)) Files.createDirectories(outputDir);

            // 3. TULIS JSON
            objectMapper.writeValue(jsonFile.toFile(), students);

            // 4. SIAPKAN COMMAND TYPST
            // Logic: typst compile [INPUT] [OUTPUT] --root [ROOT] --input key=value
            ProcessBuilder pb = new ProcessBuilder(
                "typst", 
                "compile", 
                typstRoot.resolve(REPORT_FILE).toString() + "/classroomReport.typ", // Input Full Path
                pdfFile.toString(),                        // Output Full Path
                "--root", typstRoot.toString(),            // Root Project Typst
                // Pass lokasi JSON relatif terhadap --root
                "--input", "dataFile=" + DATA_SUBDIR + "/" + jsonFile.getFileName().toString() 
            );

            pb.redirectErrorStream(true);
            Process process = pb.start();

            // Tunggu maksimal 30 detik
            boolean finished = process.waitFor(30, TimeUnit.SECONDS);
            if (!finished) {
                process.destroy();
                throw new RuntimeException("Typst process timed out");
            }

            if (process.exitValue() != 0) {
                // Baca error log dari Typst CLI
                try (java.io.InputStream is = process.getInputStream()) {
                    String errorLog = new String(is.readAllBytes());
                    throw new RuntimeException("Typst Compile Error:\n" + errorLog);
                }
            }

            // 5. BACA HASIL PDF
            return Files.readAllBytes(pdfFile);

        } catch (Exception e) {
            e.printStackTrace(); // Cek log server kalo error
            throw new RuntimeException("Gagal generate PDF: " + e.getMessage(), e);
        } finally {
            // 6. CLEANUP (Hapus file sementara)
            try {
                // Thread.sleep(1);
                Files.deleteIfExists(jsonFile);
                // Files.deleteIfExists(pdfFile);
            } catch (IOException ignored) {
                // Ignore error saat delete
            }
        }
    }
}