package com.zhang.backend2.service;

import com.zhang.backend2.model.domain.CveDo;

public interface CveService {

    void saveCve(CveDo cveDo);

    void removeCveByCveName(String name);

    void updateCve(CveDo cveDo);

    CveDo getByCveName(String name);

    CveDo getByCveDescriptionLike(String description);
}
