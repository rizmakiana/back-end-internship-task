package com.unindra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.unindra.entity.PaymentDetail;
import com.unindra.entity.Student;
import com.unindra.model.response.PaymentDetailBillResponse;
import com.unindra.model.response.StudentUnpaidResponse;
import com.unindra.entity.PaymentCategory;
import com.unindra.entity.Classroom;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentDetailRepository extends JpaRepository<PaymentDetail, String> {

    boolean existsByPaymentCategoryAndClassroomAndName(PaymentCategory paymentCategory, Classroom classroom,
            String name);

    Optional<PaymentDetail> findByPaymentCategoryAndClassroomAndName(PaymentCategory paymentCategory,
            Classroom classroom, String name);

    @Query("""
                SELECT new com.unindra.model.response.PaymentDetailBillResponse(
                    pd.paymentCategory.name,
                    pd.name,
                    pd.unitPrice
                )
                FROM PaymentDetail pd
                WHERE (pd.classroom IS NULL OR pd.classroom = :classroom)
                  AND pd.id NOT IN (
                      SELECT pi.paymentDetail.id
                      FROM PaymentItem pi
                      JOIN pi.payment p
                      WHERE p.student = :student
                  )
            """)
    List<PaymentDetailBillResponse> findUnpaidBillByStudent(
            @Param("student") Student student,
            @Param("classroom") Classroom classroom);

    @Query("""
                SELECT new com.unindra.model.response.StudentUnpaidResponse(
                    s.studentId,
                    s.name,
                    COALESCE(SUM(pd.unitPrice), 0)
                )
                FROM Student s
                JOIN s.section sec
                JOIN sec.classroom c
                JOIN PaymentDetail pd
                  ON (pd.classroom IS NULL OR pd.classroom = c)
                WHERE pd.id NOT IN (
                    SELECT pi.paymentDetail.id
                    FROM PaymentItem pi
                    WHERE pi.payment.student = s
                )
                GROUP BY s.id, s.studentId, s.name
            """)
    List<StudentUnpaidResponse> findStudentsTotalUnpaid();

    @Query("""
        SELECT pd FROM PaymentDetail pd
        WHERE pd.paymentCategory.name = :categoryName
          AND pd.name = :paymentName
          AND pd.classroom = :classroom
    """)
    Optional<PaymentDetail> findByCategoryNameAndPaymentNameAndClassroom(
            @Param("categoryName") String categoryName,
            @Param("name") String name,
            @Param("classroom") Classroom classroom
    );

    @Query("""
        SELECT pd FROM PaymentDetail pd
        WHERE pd.paymentCategory.name = :categoryName
          AND pd.name = :paymentName
          AND pd.classroom IS NULL
    """)
    Optional<PaymentDetail> findByCategoryNameAndPaymentNameAndClassroomIsNull(
            @Param("categoryName") String categoryName,
            @Param("name") String name
    );

}