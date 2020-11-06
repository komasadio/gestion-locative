package com.gestion.locative;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.gestion.locative");

        noClasses()
            .that()
            .resideInAnyPackage("com.gestion.locative.service..")
            .or()
            .resideInAnyPackage("com.gestion.locative.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.gestion.locative.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
