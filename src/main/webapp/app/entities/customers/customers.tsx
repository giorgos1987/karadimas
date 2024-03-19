import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Input, Table } from 'reactstrap';
import { byteSize, Translate, TextFormat, getSortState, JhiPagination, JhiItemCount, ValidatedForm, ValidatedField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICustomers } from 'app/shared/model/customers.model';
import { getEntities, getEntitiesByCriteria } from './customers.reducer';

export const Customers = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const customersList = useAppSelector(state => state.customers.entities);
  const loading = useAppSelector(state => state.customers.loading);
  const totalItems = useAppSelector(state => state.customers.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };
  const exportMobiles = () => {
    let mobilesOfCustomers = customersList.map((customers, i) => customers.mobile + '\n');

    var blob = new Blob(mobilesOfCustomers, { type: 'text/plain;charset=utf-8' });
    const url = URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.download = 'mobiles.txt';
    link.href = url;
    link.click();
  };
  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (location.search !== endURL) {
      navigate(`${location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const getAllSearchCustomerEntities = valuessearch => {
    console.log(valuessearch);
    dispatch(
      getEntitiesByCriteria({
        name: valuessearch.name,
        mobile: valuessearch.mobile,
        tyres: valuessearch.tyres,
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const handleSyncList = () => {
    sortEntities();
  };

  // const handleSearchCustomer = () => {
  //   getAllSearchCustomerEntities(values);
  //
  // }

  return (
    <div>
      <h2 id="customers-heading" data-cy="CustomersHeading">
        <Translate contentKey="karadimastyresApp.customers.home.title">Customers</Translate>
        <div className="d-flex justify-content-end">
          <ValidatedForm className="d-flex" onSubmit={getAllSearchCustomerEntities}>
            <ValidatedField data-cy="name" name="name" className="form-control me-sm-2" type="search" placeholder="Όνοματεπώνυμο" />
            <ValidatedField data-cy="mobile" name="mobile" className="form-control me-sm-2" type="search" placeholder="Κινητό" />
            <ValidatedField data-cy="tyres" name="tyres" className="form-control me-sm-2" type="search" placeholder="Ελαστικά" />

            <Button /*className="me-2" color="info" onClick={getAllSearchCustomerEntities} disabled={loading}*/
              color="primary"
              id="save-entity"
              data-cy="entityCreateSaveButton"
              type="submit"
            >
              <FontAwesomeIcon icon="search" spin={loading} />{' '}
              <Translate contentKey="karadimastyresApp.customers.home.customersearch">Search</Translate>
            </Button>
          </ValidatedForm>
          <Button className="me-2" color="info" onClick={exportMobiles} disabled={loading}>
            <FontAwesomeIcon icon="folder-arrow-down" spin={loading} /> Εξαγωγή Κινητών
          </Button>
          <Link to="/customers/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="karadimastyresApp.customers.home.createLabel">Create new Customers</Translate>
          </Link>
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="karadimastyresApp.customers.home.refreshListLabel">Refresh List</Translate>
          </Button>
        </div>
      </h2>
      <div className="table-responsive">
        {customersList && customersList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                {/*<th hidden className="hand" onClick={sort('id')}>*/}
                {/*  <Translate contentKey="karadimastyresApp.customers.id">ID</Translate> <FontAwesomeIcon icon="sort" />*/}
                {/*</th>*/}
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="karadimastyresApp.customers.name">Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('car')}>
                  <Translate contentKey="karadimastyresApp.customers.car">Car</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('notes')}>
                  <Translate contentKey="karadimastyresApp.customers.notes">Notes</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('mobile')}>
                  <Translate contentKey="karadimastyresApp.customers.mobile">Mobile</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tyres')}>
                  <Translate contentKey="karadimastyresApp.customers.tyres">Tyres</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('plates')}>
                  <Translate contentKey="karadimastyresApp.customers.plates">Plates</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('customertype')}>
                  <Translate contentKey="karadimastyresApp.customers.customertype">Customertype</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lastseen')}>
                  <Translate contentKey="karadimastyresApp.customers.lastseen">Lastseen</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('firstseen')}>
                  <Translate contentKey="karadimastyresApp.customers.firstseen">Firstseen</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('proselesysis')}>
                  <Translate contentKey="karadimastyresApp.customers.proselesysis">Proselesysis</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                {/*<th className="hand" onClick={sort('phone')}>*/}
                {/*  <Translate contentKey="karadimastyresApp.customers.phone">Phone</Translate> <FontAwesomeIcon icon="sort" />*/}
                {/*</th>*/}
                {/*<th className="hand" onClick={sort('companyphone')}>*/}
                {/*  <Translate contentKey="karadimastyresApp.customers.companyphone">Companyphone</Translate> <FontAwesomeIcon icon="sort" />*/}
                {/*</th>*/}
                <th className="hand" onClick={sort('email')}>
                  <Translate contentKey="karadimastyresApp.customers.email">Email</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                {/*<th className="hand" onClick={sort('addressLine')}>*/}
                {/*  <Translate contentKey="karadimastyresApp.customers.addressLine">Address Line</Translate> <FontAwesomeIcon icon="sort" />*/}
                {/*</th>*/}
                {/*<th className="hand" onClick={sort('cityCountry')}>*/}
                {/*  <Translate contentKey="karadimastyresApp.customers.cityCountry">City Country</Translate> <FontAwesomeIcon icon="sort" />*/}
                {/*</th>*/}
                <th />
              </tr>
            </thead>
            <tbody>
              {customersList.map((customers, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  {/*<td hidden>*/}
                  {/*  <Button tag={Link} to={`/customers/${customers.id}`} color="link" size="sm">*/}
                  {/*    {customers.id}*/}
                  {/*  </Button>*/}
                  {/*</td>*/}
                  <td>{customers.name}</td>
                  <td>{customers.car}</td>
                  <td>{customers.notes}</td>
                  <td>{customers.mobile}</td>
                  <td>{customers.tyres}</td>
                  <td>{customers.plates}</td>
                  <td>
                    <Translate contentKey={`karadimastyresApp.Typeofcustomer.${customers.customertype}`} />
                  </td>
                  <td>{customers.lastseen ? <TextFormat type="date" value={customers.lastseen} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{customers.firstseen ? <TextFormat type="date" value={customers.firstseen} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{customers.proselesysis}</td>
                  {/*<td>{customers.phone}</td>*/}
                  {/*<td>{customers.companyphone}</td>*/}
                  <td>{customers.email}</td>
                  {/*<td>{customers.addressLine}</td>*/}
                  {/*<td>{customers.cityCountry}</td>*/}
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/customers/${customers.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      {/*<Button tag={Link} to={`/customersdtpayments/${customers.id}`} color="info" size="sm" data-cy="entityCustomersdtpaymentsButton">*/}
                      {/*  <FontAwesomeIcon icon="eye" />{' '}*/}
                      {/*  <span className="d-none d-md-inline">*/}
                      {/*    Πληρωμές*/}
                      {/*  </span>*/}
                      {/*</Button>*/}
                      <Button
                        tag={Link}
                        to={`/customers/${customers.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/customers/${customers.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="karadimastyresApp.customers.home.notFound">No Customers found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={customersList && customersList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Customers;
