import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDossierPharmacie } from 'app/shared/model/dossier-pharmacie.model';
import { getEntities } from './dossier-pharmacie.reducer';

export const DossierPharmacie = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const dossierPharmacieList = useAppSelector(state => state.dossierPharmacie.entities);
  const loading = useAppSelector(state => state.dossierPharmacie.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="dossier-pharmacie-heading" data-cy="DossierPharmacieHeading">
        <Translate contentKey="drugStoreApp.dossierPharmacie.home.title">Dossier Pharmacies</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="drugStoreApp.dossierPharmacie.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/dossier-pharmacie/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="drugStoreApp.dossierPharmacie.home.createLabel">Create new Dossier Pharmacie</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {dossierPharmacieList && dossierPharmacieList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="drugStoreApp.dossierPharmacie.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierPharmacie.numero">Numero</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierPharmacie.dateDepot">Date Depot</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierPharmacie.nature">Nature</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierPharmacie.statut">Statut</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierPharmacie.dateDerniereModif">Date Derniere Modif</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierPharmacie.commentaire">Commentaire</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierPharmacie.nom">Nom</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierPharmacie.prenom">Prenom</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierPharmacie.telephone">Telephone</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierPharmacie.cIN">C IN</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierPharmacie.user">User</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierPharmacie.documentList">Document List</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierPharmacie.utilisateur">Utilisateur</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierPharmacie.local">Local</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {dossierPharmacieList.map((dossierPharmacie, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/dossier-pharmacie/${dossierPharmacie.id}`} color="link" size="sm">
                      {dossierPharmacie.id}
                    </Button>
                  </td>
                  <td>{dossierPharmacie.numero}</td>
                  <td>
                    {dossierPharmacie.dateDepot ? (
                      <TextFormat type="date" value={dossierPharmacie.dateDepot} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{dossierPharmacie.nature}</td>
                  <td>{dossierPharmacie.statut}</td>
                  <td>
                    {dossierPharmacie.dateDerniereModif ? (
                      <TextFormat type="date" value={dossierPharmacie.dateDerniereModif} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{dossierPharmacie.commentaire}</td>
                  <td>{dossierPharmacie.nom}</td>
                  <td>{dossierPharmacie.prenom}</td>
                  <td>{dossierPharmacie.telephone}</td>
                  <td>{dossierPharmacie.cIN}</td>
                  <td>{dossierPharmacie.user ? dossierPharmacie.user.id : ''}</td>
                  <td>
                    {dossierPharmacie.documentLists
                      ? dossierPharmacie.documentLists.map((val, j) => (
                          <span key={j}>
                            <Link to={`/document/${val.id}`}>{val.id}</Link>
                            {j === dossierPharmacie.documentLists.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {dossierPharmacie.utilisateur ? (
                      <Link to={`/utilisateur/${dossierPharmacie.utilisateur.id}`}>{dossierPharmacie.utilisateur.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {dossierPharmacie.local ? <Link to={`/local/${dossierPharmacie.local.id}`}>{dossierPharmacie.local.id}</Link> : ''}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/dossier-pharmacie/${dossierPharmacie.id}`}
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
                        to={`/dossier-pharmacie/${dossierPharmacie.id}/edit`}
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
                        to={`/dossier-pharmacie/${dossierPharmacie.id}/delete`}
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
              <Translate contentKey="drugStoreApp.dossierPharmacie.home.notFound">No Dossier Pharmacies found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default DossierPharmacie;
