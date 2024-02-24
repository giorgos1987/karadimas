import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISupplierpayments } from 'app/shared/model/supplierpayments.model';
import { getEntities } from './supplierpayments.reducer';

export const Supplierpayments = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const supplierpaymentsList = useAppSelector(state => state.supplierpayments.entities);
  const loading = useAppSelector(state => state.supplierpayments.loading);
  const totalItems = useAppSelector(state => state.supplierpayments.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
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

  const handleSyncList = () => {
    sortEntities();
  };

  return (
    <div>
      <h2 id="supplierpayments-heading" data-cy="SupplierpaymentsHeading">
        <Translate contentKey="karadimastyresApp.supplierpayments.home.title">Supplierpayments</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="karadimastyresApp.supplierpayments.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/supplierpayments/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="karadimastyresApp.supplierpayments.home.createLabel">Create new Supplierpayments</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {supplierpaymentsList && supplierpaymentsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="karadimastyresApp.supplierpayments.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('invoiceDate')}>
                  <Translate contentKey="karadimastyresApp.supplierpayments.invoiceDate">Invoice Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dueDate')}>
                  <Translate contentKey="karadimastyresApp.supplierpayments.dueDate">Due Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('ispaid')}>
                  <Translate contentKey="karadimastyresApp.supplierpayments.ispaid">Ispaid</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('amountDue')}>
                  <Translate contentKey="karadimastyresApp.supplierpayments.amountDue">Amount Due</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('notes')}>
                  <Translate contentKey="karadimastyresApp.supplierpayments.notes">Notes</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('attachments')}>
                  <Translate contentKey="karadimastyresApp.supplierpayments.attachments">Attachments</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tax')}>
                  <Translate contentKey="karadimastyresApp.supplierpayments.tax">Tax</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('shipping')}>
                  <Translate contentKey="karadimastyresApp.supplierpayments.shipping">Shipping</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lastupdated')}>
                  <Translate contentKey="karadimastyresApp.supplierpayments.lastupdated">Lastupdated</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.supplierpayments.supplier">Supplier</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {supplierpaymentsList.map((supplierpayments, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/supplierpayments/${supplierpayments.id}`} color="link" size="sm">
                      {supplierpayments.id}
                    </Button>
                  </td>
                  <td>
                    {supplierpayments.invoiceDate ? (
                      <TextFormat type="date" value={supplierpayments.invoiceDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {supplierpayments.dueDate ? <TextFormat type="date" value={supplierpayments.dueDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    <Translate contentKey={`karadimastyresApp.Paystatus.${supplierpayments.ispaid}`} />
                  </td>
                  <td>{supplierpayments.amountDue}</td>
                  <td>{supplierpayments.notes}</td>
                  <td>
                    {supplierpayments.attachments ? (
                      <div>
                        {supplierpayments.attachmentsContentType ? (
                          <a onClick={openFile(supplierpayments.attachmentsContentType, supplierpayments.attachments)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {supplierpayments.attachmentsContentType}, {byteSize(supplierpayments.attachments)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{supplierpayments.tax}</td>
                  <td>{supplierpayments.shipping}</td>
                  <td>
                    {supplierpayments.lastupdated ? (
                      <TextFormat type="date" value={supplierpayments.lastupdated} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {supplierpayments.supplier ? (
                      <Link to={`/supplier/${supplierpayments.supplier.id}`}>{supplierpayments.supplier.company}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/supplierpayments/${supplierpayments.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/supplierpayments/${supplierpayments.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/supplierpayments/${supplierpayments.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="karadimastyresApp.supplierpayments.home.notFound">No Supplierpayments found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={supplierpaymentsList && supplierpaymentsList.length > 0 ? '' : 'd-none'}>
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

export default Supplierpayments;
