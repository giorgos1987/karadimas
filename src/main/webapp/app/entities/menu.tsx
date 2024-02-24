import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/job">
        <Translate contentKey="global.menu.entities.job" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/job-category">
        <Translate contentKey="global.menu.entities.jobCategory" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/customer-details">
        <Translate contentKey="global.menu.entities.customerDetails" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/cart">
        <Translate contentKey="global.menu.entities.cart" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/supplierpayments">
        <Translate contentKey="global.menu.entities.supplierpayments" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/booking">
        <Translate contentKey="global.menu.entities.booking" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/supplier">
        <Translate contentKey="global.menu.entities.supplier" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/customerpayments">
        <Translate contentKey="global.menu.entities.customerpayments" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/statistics">
        <Translate contentKey="global.menu.entities.statistics" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/company-profile">
        <Translate contentKey="global.menu.entities.companyProfile" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
