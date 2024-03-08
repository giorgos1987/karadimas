import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="car" to="/cart">
        <Translate contentKey="global.menu.entities.cart" />
      </MenuItem>
      <MenuItem icon="user-circle" to="/customers">
        <Translate contentKey="global.menu.entities.customers" />
      </MenuItem>
      <MenuItem icon="credit-card" to="/customerpayments">
        <Translate contentKey="global.menu.entities.customerpayments" />
      </MenuItem>
      <MenuItem icon="basket-shopping" to="/neworders">
        <Translate contentKey="global.menu.entities.neworders" />
      </MenuItem>
      <MenuItem icon="tools" to="/job">
        <Translate contentKey="global.menu.entities.job" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
