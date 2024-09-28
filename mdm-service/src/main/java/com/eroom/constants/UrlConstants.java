package com.eroom.constants;

public class UrlConstants {


    private UrlConstants() {
        throw new IllegalArgumentException("UrlConstants is a utility class");
    }

    // BASE URLS
    public static final String BASE_API_V1_USER = "/api/v1/user";
    public static final String BASE_API_V1_ROLE = "/api/v1/role";
    public static final String BASE_API_V1_ORGANISATION = "/api/v1/organisation";
    public static final String BASE_API_V1_DIVISION = "/api/v1/division";
    
    public static final String BASE_API_V1_DOCUMENT = "/api/v1/documentType";
    public static final String BASE_API_V1_DOCUMENT_ASSIGNMENT = "/api/v1/documentAssignment";
    public static final String BASE_API_V1_GEOGRAPHICAL_TERRITORY = "/api/v1/geographicalTerritory";
    public static final String BASE_API_V1_EMPLOYEE = "/api/v1/employee";
    public static final String BASE_API_V1_MENU_AND_PAGE = "/api/v1/menuAndPage";
    public static final String BASE_API_V1_SUPPLY_VERTICAL = "/api/v1/supplyVertical";
    public static final String BASE_API_V1_PROJECT_MANAGER = "/api/v1/projectManager";

    // SWAGGER API URLS
    public static final String SWAGGER_DOCS_URL = "/v3/api-docs/**";
    public static final String SWAGGER_UI_URL = "/swagger-ui/**";

    // APIS URLS
    public static final String ADD_USER = "/addUser";
    public static final String GET_ALL_USER = "/getAllUsers";
    public static final String DELETE_USER_DETAILS = "/deleteUserDetails";
    public static final String USER_DETAILS_UPDATE = "/userDetailsUpdate";
    public static final String ADD_ROLE = "/addRole";
    public static final String GET_ALL_ROLES = "/getAllRoles";
    public static final String GET_ALL_ORGANISATION = "/getAllOranisation";
    public static final String UPDATE_ROLE = "/updateRole";
    public static final String DELETE_ROLE = "/deleteRole";
    public static final String GET_ALL_DIVISIONS = "/getAllDivisions";
    public static final String GET_ALL_GEO_GRAPHICAL_TERRITORIES = "/getAllGeoGraphicalTerritories";
    public static final String GET_ALL_EMPLOYEES = "/getAllEmployees";
    public static final String GET_MENU_SUB_PAGES_LIST = "/getMenuSubPagesList";
    public static final String ADD_PAGE_ACCESS_RIGHT = "/addPageAccessRight";
    public static final String UPDATE_PAGE_ACCESS_RIGHT = "/updatePageAccessRight";
    public static final String ADD_SUPPLY_VERTICAL = "/addSupplyVertical";
    public static final String GET_ALL_SUPPLY_VERTICAL = "/getAllSupplyVertical";
    public static final String UPDATE_SUPPLY_VERTICAL = "/updateSupplyVertical";
    public static final String DELETE_SUPPLY_VERTICAL = "/deleteSupplyVertical";
    public static final String GET_USER_DETAILS_BY_ID = "/getUserDetailsById";
    public static final String GET_DOCUMENT_ASSIGNMENT_BY_ID = "/getDocumentAssinedById";
    public static final String DUPLICATE_USER_NAME_CHECK = "/duplicateUserNameCheck";
    public static final String GET_PAGES_LIST_WITH_ROLES = "/getPagesListWithRoles";

    public static final String GET_DOCUMENT_TYPE = "/getDocumentType";
    public static final String DELETE_MENU_AND_SUB_MENU_PAGE_BY_ROLE_ID = "/deleteMenuAndSubMenuPagesByRoleId";
    public static final String ADD_DOCUMENT_TYPE = "/addDocumentType";
    public static final String ADD_DOCUMENT_ASSIGNMENT = "/addDocumentAssignment";
    public static final String GET_EMPLOYEE_NUMBER_LIST = "/getEmployeeNumberList";
    public static final String DELETE_DOCUMENT_TYPE = "/deleteDocumentType";
    public static final String DELETE_DOCUMENT_ASSIGNED = "/deleteDocumentAssigned";
    public static final String UPDATE_DOCUMENT_TYPE = "/updateDocumentType";
    public static final String UPDATE_DOCUMENT_ASSIGNMENT = "/updateDocumentAssignment";
    public static final String GET_ALL_DOC_ASSIGNED = "/getAllDocAssignment";
    public static final String GET_DOC_TYPE_AND_DOC_NAME = "/getDocumentTypeAndDocumentNameList";
    public static final String GET_FUNCTIONAL_USER_LIST = "/getFunctionalUserList";
    public static final String GET_CHECKER_USER_LIST = "/getCheckerUserList";
    public static final String GET_APPROVER_USER_LIST = "/getApproverUserList";
    public static final String GET_EMPLOYEE_USER_NAME = "/getEmployeeUsername";
    public static final String GET_DEPARTMENT_LIST = "/getDepartmentList";
    public static final String ADD_ORGANISATION = "/addOrganisation";
    public static final String GET_PROJECT_MANAGER_LIST = "/getProjectManagerList";
    public static final String GET_DOCUMENT_TYPE_BY_ID = "/getDocumentTypeById";
    public static final String GET_DETAILS_OF_DOC = "/getDetailsOfDocument";
    public static final String GET_DEPARTMENT_BY_DOCUMENT_TYPE = "/getDepartmentByDocumentName";
    public static final String GET_SUPPLY_VERTICAL_BY_DOCUMENT_TYPE = "/getSupplyVerticalByDocumentName";
    public static final String GET_DIVISION_BY_DOCUMENT_TYPE = "/getDivisionByDocumentName";
}
