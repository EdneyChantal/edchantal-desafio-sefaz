package com.edchantal.appbanco;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.edchantal.appbanco");

        noClasses()
            .that()
                .resideInAnyPackage("com.edchantal.appbanco.service..")
            .or()
                .resideInAnyPackage("com.edchantal.appbanco.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.edchantal.appbanco.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
