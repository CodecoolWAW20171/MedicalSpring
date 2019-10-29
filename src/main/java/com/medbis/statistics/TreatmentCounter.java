package com.medbis.statistics;

import com.medbis.entity.Treatment;
import com.medbis.entity.VisitTreatment;
import com.medbis.service.impl.VisitTreatmentServiceImpl;
import com.medbis.service.interfaces.TreatmentService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class TreatmentCounter {

    private VisitTreatmentServiceImpl visitTreatmentService;
    private TreatmentService treatmentService;



    public TreatmentCounter(VisitTreatmentServiceImpl visitTreatmentService, TreatmentService treatmentService) {
        this.visitTreatmentService = visitTreatmentService;
        this.treatmentService = treatmentService;
    }

    public Map<Treatment, Integer> takeSumOfOneTreatmentDoneInMonth(int month){
        Map<Treatment, Integer> sumOfSingleTreatmentDoneInMonth =
                Util.getInstance().
                        createMapOfSingleTreatmentResultInMonth(treatmentService.findAll());

        List<VisitTreatment> visitTreatments = visitTreatmentService.getVisitTreatmentsDoneInMonth(LocalDate.of(2019,month,1), Util.getInstance().getLastDayOfMonth(month));
        int result;
        Treatment treatment;

        for(VisitTreatment visitTreatment : visitTreatments){
            treatment = visitTreatment.getTreatment();
            result = visitTreatmentService.
                    takeSumOfOneTreatmentDoneInMonth(treatment.getTreatmentId(),
                            LocalDate.of(2019,month,1), Util.getInstance().getLastDayOfMonth(month));
            sumOfSingleTreatmentDoneInMonth.put(treatment, result);
        }
        return sumOfSingleTreatmentDoneInMonth;



    }

}
