
# GUIDE

## Convention when creating merge request

### how to name a branch?
bug fix --> bugfix/FEM-<ID>\
feature --> feat/FEM-<ID>\
others --> chore/FEM-<ID>\

e.g.\
feat/FEM-25, bugfix/FEM-26

### how to set title of merge request?
feat: [FEM-25]: Your content here\
bugfix: [FEM-26]: Your content here

## Backend Deployment
### How to restart app service?
In order to reset service, please click on the [run workflow](https://github.com/CharmFlex-98/FlexiExpensesManager_Backend/actions/workflows/restart-app-service.yml) at the top right corner.

### How to restart database service?
You cannot do that. Only owner of the repo can restart database as restarting this service will delete all users' data.
