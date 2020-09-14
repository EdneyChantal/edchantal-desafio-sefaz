package com.edchantalsefaz.gateway;

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
            .importPackages("com.edchantalsefaz.gateway");

        noClasses()
            .that()
                .resideInAnyPackage("com.edchantalsefaz.gateway.service..")
            .or()
                .resideInAnyPackage("com.edchantalsefaz.gateway.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.edchantalsefaz.gateway.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
