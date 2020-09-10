package com.edchantalsefaz.apibank;

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
            .importPackages("com.edchantalsefaz.apibank");

        noClasses()
            .that()
                .resideInAnyPackage("com.edchantalsefaz.apibank.service..")
            .or()
                .resideInAnyPackage("com.edchantalsefaz.apibank.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.edchantalsefaz.apibank.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
